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
public class EmployeesVsGenderPieChart {

    public static  DefaultPieDataset getDataset() {
        DefaultPieDataset defaultPieDataset = new DefaultPieDataset();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payrollSystem","root","");
            PreparedStatement malePopulationPreparedStatement = connection.prepareStatement(""
                    + "SELECT COUNT(*) FROM employeebasicDetails WHERE gender = 'male';");
            PreparedStatement femalePopulationPreparedStatement = connection.prepareStatement(""
                    + "SELECT COUNT(*) FROM employeebasicDetails WHERE gender = 'female';");
            ResultSet maleEmployees = malePopulationPreparedStatement.executeQuery();
            ResultSet femaleEmployees = femalePopulationPreparedStatement.executeQuery();
            while(maleEmployees.next()){
                defaultPieDataset.setValue("Male", new Double(maleEmployees.getString(1)));
            }
            while(femaleEmployees.next()){
                 defaultPieDataset.setValue("Feamle", new Double(femaleEmployees.getString(1)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }

        return defaultPieDataset;
    }

    public static ChartPanel getChartPanel() {
        JFreeChart pieChart = ChartFactory.createPieChart("Employee Vs Gender", getDataset());
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBounds(2, 5, 550, 355);
        chartPanel.setBorder(new LineBorder(Color.decode("#f5f5f5"), 2));

        return chartPanel;
    }
}
