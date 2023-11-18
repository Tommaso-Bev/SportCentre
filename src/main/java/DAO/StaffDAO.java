package DAO;

import DomainModel.Staff;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class StaffDAO implements DAO<Staff> {
    public StaffDAO(SportsCentreDAO sportsCentreDAO) {
        this.sportsCentreDAO = sportsCentreDAO;
    }

    private SportsCentreDAO sportsCentreDAO;

    @Override
    public Staff get(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        Staff staff = null;
        PreparedStatement prepStat = connection.prepareStatement("SELECT * FROM staff WHERE ID= ?");
        prepStat.setInt(1, id);
        ResultSet resSet = prepStat.executeQuery();
        if (resSet.next()) {
            staff = new Staff(id, resSet.getString("codFisc"), resSet.getString("firstName"), resSet.getString("surname"), LocalDate.parse(resSet.getString("hiringDate")), resSet.getString("task"), resSet.getInt("salary"), sportsCentreDAO.get(resSet.getInt("sportCentre")));
        }
        resSet.close();
        prepStat.close();
        connection.close();
        return staff;
    }

    @Override
    public ArrayList<Staff> getAll() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        ArrayList<Staff> staff = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM staff");
        ResultSet resSet = ps.executeQuery();
        while (resSet.next()) {
            staff.add(new Staff(resSet.getInt("ID"), resSet.getString("codFisc"), resSet.getString("firstName"), resSet.getString("surname"), LocalDate.parse(resSet.getString("hiringDate")), resSet.getString("task"), resSet.getInt("salary"), sportsCentreDAO.get(resSet.getInt("sportCentre"))));
        }
        resSet.close();
        ps.close();
        connection.close();
        return staff;
    }


    @Override
    public void save(Staff staff) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO staff (ID, codFisc, firstName,surname, hiringDate, task, salary,sportsCentresID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, getNextId());
        ps.setString(2, staff.getName());
        ps.setString(3, staff.getSurname());
        ps.setString(4, staff.getCodFisc());
        ps.setString(5, staff.getTask());
        ps.setInt(6, staff.getSalary());
        ps.setInt(7, staff.getSportsCentre().getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }


    @Override
    public void modify(Staff staff, String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("UPDATE staff SET codFisc = ?, firstName = ?, surname = ?,hiringDate = ?,task = ?, salary=?, sportsCentresID=?  WHERE ID = ?");
        ps.setString(1, args[0]);
        ps.setString(2, args[1]);
        ps.setString(3, args[2]);
        ps.setString(4, args[3]);
        ps.setString(5, args[4]);
        ps.setString(6, args[5]);
        ps.setInt(7, Integer.parseInt(args[6]));
        ps.setInt(8, staff.getID());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void remove(int id) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        PreparedStatement ps = connection.prepareStatement("DELETE FROM staff WHERE ID = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override

    public int getNextId() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "sportCentre.sqlite");
        String query = "SELECT MAX(ID) FROM staff";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        int id; //TODO check if it works
        if (rs.next()) {
            id = rs.getInt(1) + 1;
        } else {
            id = 1;
        }

        rs.close();
        statement.close();
        connection.close();
        return id;
    }
}
