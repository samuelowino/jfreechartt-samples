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
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author HACKER
 */
public class NHIF_NSSF_PAYE_WELFARE_LOAN_PieChart {

    public static final DefaultPieDataset getDataSet() {

        DefaultPieDataset defaultPieDataset = new DefaultPieDataset();

        try (Connection connection = DriverManager.getConnection(""
                + "jdbc:mysql://localhost:3306/payrollSystem", "root", "")) {
            PreparedStatement preparedStatement = connection.prepareStatement(""
                    + "SELECT SUM(PAYE) AS PAYE, SUM(NHIF) AS NHIF,SUM(NSSF) AS NSSF"
                    + ",SUM(W_Loan) AS W_Loan,SUM(S_Contributions) AS S_Contributions"
                    + " FROM previousePayments;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                defaultPieDataset.setValue("NHIF", new Integer(resultSet.getString("NHIF")));
                defaultPieDataset.setValue("NSSF", new Integer(resultSet.getString("NSSF")));
                defaultPieDataset.setValue("PAYE", new Integer(resultSet.getString("PAYE")));
                defaultPieDataset.setValue("W_LOAN", new Integer(resultSet.getString("W_LOAN")));
                defaultPieDataset.setValue("S_Contributions", new Integer(resultSet.getString("S_Contributions")));
            }
            return defaultPieDataset;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return new DefaultPieDataset();
        }
    }

    public static final ChartPanel getChartPanel() {
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Salary Deductions Analysis",
                getDataSet(),
                true,
                true,
                true);
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBounds(2, 5, 550, 355);
        chartPanel.setBorder(new LineBorder(Color.decode("#f5f5f5"), 2));
        
        return chartPanel;
    }
}
