/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessIntelligence;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author HACKER
 */
public class MonthlyContributionsBarChat {

    public static final DefaultCategoryDataset getDataSet(int year,int month,int day) {
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

        try(Connection connection = DriverManager.getConnection(""
                + "jdbc:mysql://localhost:3306/payrollSystem","root","")){
            LocalDate setDate = LocalDate.of(year,month,day);
            
            PreparedStatement NHIFPreparedStatement = connection.prepareStatement(""
                    + "SELECT SUM(NHIF) AS NHIF FROM previousePayments WHERE payMonth = ? AND Year = ?");
            PreparedStatement NSSFPreparedStatement = connection.prepareStatement(""
                    + "SELECT SUM(NSSF) AS NSSF FROM previousePayments WHERE payMonth = ? AND Year = ?");
            PreparedStatement PAYEPreparedStatement = connection.prepareStatement(""
                    + "SELECT SUM(PAYE) AS PAYE FROM previousePayments WHERE payMonth = ? AND Year = ?");
            PreparedStatement WelfareLoanPreparedStatement = connection.prepareStatement( ""
                    + "SELECT SUM(W_Loan) AS WelfareLoan FROM previousePayments WHERE payMonth = ? AND Year = ?");
            PreparedStatement S_ContributionsPreparedStatement = connection.prepareStatement(""
                    + "SELECT SUM(S_Contributions) AS S_CONTRIBUTIONS FROM previousePayments WHERE payMonth = ? AND Year = ?");
            
            
            NHIFPreparedStatement.setString(1,setDate.getMonth().toString());
            NHIFPreparedStatement.setString(2,Integer.toString(year));
            NSSFPreparedStatement.setString(1,setDate.getMonth().toString());
            NSSFPreparedStatement.setString(2,Integer.toString(year));
            PAYEPreparedStatement.setString(1,setDate.getMonth().toString());
            PAYEPreparedStatement.setString(2,Integer.toString(year));
            WelfareLoanPreparedStatement.setString(1,setDate.getMonth().toString());
            WelfareLoanPreparedStatement.setString(2,Integer.toString(year));
            S_ContributionsPreparedStatement.setString(1,setDate.getMonth().toString());
            S_ContributionsPreparedStatement.setString(2,Integer.toString(year));
            
            
            ResultSet NHIFResultSet = NHIFPreparedStatement.executeQuery();
            ResultSet NSSFResultSet = NSSFPreparedStatement.executeQuery();
            ResultSet PAYEResultSet = PAYEPreparedStatement.executeQuery();
            ResultSet W_LOANResultSet = WelfareLoanPreparedStatement.executeQuery();
            ResultSet S_ContributionsResultSet = S_ContributionsPreparedStatement.executeQuery();
            
            while(NHIFResultSet.next()){
                
                String NHIFAmount = NHIFResultSet.getString("NHIF");
                defaultCategoryDataset.addValue(new Double(NHIFAmount), "Amount Deducted","NHIF");
                
            }
            while(NSSFResultSet.next()){
                
                String NSSFAmount = NSSFResultSet.getString("NSSF");
                defaultCategoryDataset.addValue(new Double(NSSFAmount), "Amount Deducted","NSSF");
                
            }
            
            while(PAYEResultSet.next()){
                
                String PAYEAmount = PAYEResultSet.getString("PAYE");
                defaultCategoryDataset.addValue(new Double(PAYEAmount), "Amount Deducted","PAYE");
                
            }
            
            while(W_LOANResultSet.next()){
                
                String W_LOANAmount = W_LOANResultSet.getString("WelfareLoan");
                defaultCategoryDataset.addValue(new Double(W_LOANAmount), "Amount Deducted","W_LOAN");
                
            }
            
            while(S_ContributionsResultSet.next()){
                
                String S_Contributions = S_ContributionsResultSet.getString("S_CONTRIBUTIONS");
                defaultCategoryDataset.addValue(new Double(S_Contributions), "Amount Deducted","S_Contributions");
                
            }
            
            
            return defaultCategoryDataset;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return new DefaultCategoryDataset();
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null,"We did not find any payments data related to the specified date.!!");
            return new DefaultCategoryDataset();
        }
    }
    
    public static final ChartPanel getChartPanel(){
        JFreeChart barChart = ChartFactory.createBarChart(
                "Total Monthly Deductions(NHIF,NSSF,PAYE,W/C)", 
                "Deduction Type", 
                "Total Contribution",
                getDataSet(2016,10,20), 
                PlotOrientation. VERTICAL,
                true, 
                true, 
                true);
        
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBounds(2, 5, 550, 355);
        chartPanel.setBorder(new LineBorder(Color.decode("#f5f5f5"), 2));
        
        return chartPanel;
    }
}
