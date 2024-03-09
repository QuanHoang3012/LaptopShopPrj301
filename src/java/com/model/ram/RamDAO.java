/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.ram;

import com.model.utils.DBUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anh Quan
 */
public class RamDAO extends DBUtils {

    public List<Ram> getAll() {
        List<Ram> list = new ArrayList<>();
        String sql = "Select * from ram";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Ram m = new Ram(rs.getInt("id"), rs.getString("detail"));
                list.add(m);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public boolean deleteRam(int id) {
        String sql = "DELETE FROM [dbo].[ram]\n"
                + "      WHERE id=" + id;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean insertRam(String name) {
        String sql = "INSERT INTO [dbo].[ram]\n"
                + "           ([detail])\n"
                + "     VALUES\n"
                + "           (?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.executeUpdate();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public Ram getRambyId(int id) {
        List<Ram> list = new ArrayList<>();
        String sql = "Select * from  [Laptop_Shop].[dbo].[ram] where 1=1";
        if (id != 0) {
            sql += "and id=" + id;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Ram m = new Ram(rs.getInt("id"), rs.getString("detail"));
                return m;
            }
        } catch (SQLException e) {
        }
        return null;
    }
}
