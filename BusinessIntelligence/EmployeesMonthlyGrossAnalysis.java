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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author HACKER
 */
public class EmployeesMonthlyGrossAnalysis {

    public static final DefaultCategoryDataset getDataSet() {
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

        try (Connection connection = DriverManager.getConnection(""
                + "jdbc:mysql://localhost:3306/payrollSystem", "root", "")) {
            PreparedStatement preparedStatement = connection.prepareStatement(""
                    + "SELECT gross FROM employeeSalaries;");
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 1;
            while (resultSet.next()) {

                defaultCategoryDataset.setValue(new Integer(resultSet.getString(1)), 
                        "Salary", "Employee" + i);
                i++;
            }
            return defaultCategoryDataset;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return new DefaultCategoryDataset();
        }
    }

    public static final ChartPanel getChartPanel() {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Employees Gross Income",
                "Amount",
                "Individual Employees",
                getDataSet(),
                PlotOrientation.HORIZONTAL,
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
