package com.demo.db;

import com.demo.util.Tools;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.lang.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class DBConnection {

    public static String DBURL  = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8";
    public static String DBUSER = "root";
    public static String DBPAS  = "root";

    static {
        try {
            ClassLoader cl = null;
            try {
                cl = Thread.currentThread().getContextClassLoader();
            } catch (Throwable ex) {
                // Cannot access thread context ClassLoader - falling back
                // to system class loader...
            }
            if (cl == null) {
                // No thread context class loader -> use class loader of
                // this class.
                cl = ClassUtils.class.getClassLoader();
            }

            InputStream in = cl.getResourceAsStream("jdbc.properties");
            Properties property = new Properties();
            property.load(in);
            in.close();

            String dbURL = (String) property.get("jdbc.url");
            if (dbURL != null && !"".equals(dbURL)) {
                DBURL = dbURL;
            }

            String dbUser = (String) property.get("jdbc.username");
            if (dbUser != null && !"".equals(dbUser)) {
                DBUSER = dbUser;
            }

            String dbPas = (String) property.get("jdbc.password");
            if (dbPas != null && !"".equals(dbPas)) {
                DBPAS = dbPas;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
    }

    public boolean addAccount(String id, String name, String pas) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("insert into sysadmin(adm_id,adm_name,adm_pwd) values(?,?,?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, pas);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    public boolean updateAccount(String id, String pas) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("update sysadmin set adm_pwd = ? where adm_id = ?");
            stmt.setString(2, id);
            stmt.setString(1, pas);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    public boolean updateStaff(String id, String name, String pas, int type) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("update user set usr_name = ? ,usr_pwd = ?, usr_type = ? where usr_id = ?");
            stmt.setString(4, id);
            stmt.setString(1, name);
            stmt.setString(2, pas);
            stmt.setInt(3, type);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    public boolean kickoffStaff(String id) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("update user set secret_token = null ,signature_key = null where usr_id = ?");
            stmt.setString(1, id);
            stmt.executeUpdate();
            stmt.close();
            stmt = null;

            stmt = con
                    .prepareStatement("update device set status = null ,login_dt = null,update_dt = null, usr_id = null where usr_id = ?");
            stmt.setString(1, id);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    public boolean deleteAccount(String id) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("delete from sysadmin where adm_id = ?");
            stmt.setString(1, id);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    public boolean addStaff(String id, String name, String pas,int type) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("insert into user(usr_id,usr_name,usr_pwd,usr_type) values(?,?,?,?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, pas);
            stmt.setInt(4, type);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    public boolean addBarcode(int count) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("insert into device(bar_code) values(?)");
            for (int i = 1; i <= count; i++) {
                if (i < 10) {
                    stmt.setString(1, String.valueOf(Tools.getCurrentDT2() + "0" + i));
                } else {
                    stmt.setString(1, String.valueOf(Tools.getCurrentDT2() + i));
                }

                stmt.addBatch();
            }
            stmt.executeBatch();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    public boolean deleteStaff(String id) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("delete from user where usr_id = ?");
            stmt.setString(1, id);
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    public boolean deleteBarcode(String id) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            stmt = con.prepareStatement("SELECT serial_no FROM device WHERE bar_code = ?");
            stmt.setString(1, id);
            // 执行查询，并获得结果对象
            ResultSet rs = stmt.executeQuery();
            boolean result = true;
            while (rs.next()) {
                if (!StringUtils.isNullOrEmpty(rs.getString("serial_no"))) {
                    result = false;
                }
            }
            rs.close();
            if (result) {
                // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
                stmt = con.prepareStatement("delete from device where bar_code = ?");
                stmt.setString(1, id);
                stmt.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }

    }

    public AdminVO login(String aid, String apas) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        AdminVO adminVO = null;
        try {
            // 创建数据库连接
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("SELECT * FROM sysadmin WHERE adm_id = ? AND adm_pwd = ?");
            stmt.setString(1, aid);
            stmt.setString(2, apas);
            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                adminVO = new AdminVO();
                adminVO.setName(rs.getString("adm_name"));
                adminVO.setAdmType(rs.getInt("adm_type"));
                adminVO.setId(aid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return adminVO;
    }

    public List<AdminVO> listAdmin() {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<AdminVO> adminVOList = new ArrayList<AdminVO>();
        try {
            // 创建数据库连接
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("SELECT * FROM sysadmin");

            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                AdminVO adminVO = new AdminVO();
                adminVO.setName(rs.getString("adm_name"));
                adminVO.setId(rs.getString("adm_id"));
                adminVO.setAdmType(rs.getInt("adm_type"));
                adminVOList.add(adminVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return adminVOList;
    }

    public List<StaffVO> listStaff() {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<StaffVO> adminVOList = new ArrayList<StaffVO>();
        try {
            // 创建数据库连接
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("SELECT * FROM user");

            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                StaffVO adminVO = new StaffVO();
                adminVO.setName(rs.getString("usr_name"));
                adminVO.setId(rs.getString("usr_id"));
                adminVO.setUserType(rs.getInt("usr_type"));
                adminVO.setSecretToken(rs.getString("secret_token"));
                adminVOList.add(adminVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return adminVOList;
    }

    public List<BarcdVO> listBarcode() {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<BarcdVO> adminVOList = new ArrayList<BarcdVO>();
        try {
            // 创建数据库连接
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con.prepareStatement("SELECT * FROM device");

            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                BarcdVO adminVO = new BarcdVO();
                adminVO.setBarcode(rs.getString("bar_code"));
                adminVO.setDeviceNo(rs.getString("serial_no"));
                adminVO.setStatus(rs.getInt("status"));
                adminVOList.add(adminVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return adminVOList;
    }

    public List<TerminalVO> listTerminal(int pageCount, int pageNumber) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<TerminalVO> adminVOList = new ArrayList<TerminalVO>();
        try {
            // 创建数据库连接
            con = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            stmt = con
                    .prepareStatement("SELECT * FROM device WHERE serial_no is not null ORDER BY bar_code ASC LIMIT ?,?");
            stmt.setInt(1, (pageNumber - 1) * pageCount);
            stmt.setInt(2, (pageNumber) * pageCount);
            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                TerminalVO adminVO = new TerminalVO();
                adminVO.setBarcode(rs.getString("bar_code"));
                adminVO.setDeviceNo(rs.getString("serial_no"));
                adminVO.setStatus(rs.getInt("status"));
                adminVO.setLoginDt(rs.getTimestamp("login_dt"));
                adminVO.setUpdDt(rs.getTimestamp("update_dt"));
                adminVO.setUsrId(rs.getString("usr_id"));
                adminVOList.add(adminVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return adminVOList;
    }

    public List<PictureVO> listPicture(String uid, String from, String to, int type, int pageCount, int pageNumber) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<PictureVO> adminVOList = new ArrayList<PictureVO>();
        Date f = Tools.parseDT2(from+" 00:00:00");
        f = (f == null ? Tools.parseDT("1997-12-31"): f);
        Date t = Tools.parseDT2(to+" 23:59:59");
        t = (t == null ? Tools.parseDT("2017-12-31"): t);
        try {
            // 创建数据库连接
            con  = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            if(uid == null || uid.trim().equals("")){
                stmt = con.prepareStatement("SELECT * FROM picture WHERE pic_type = ? and create_dt between ? and ? ORDER BY upload_dt DESC LIMIT ?,?");
                stmt.setInt(1, type);
                stmt.setTimestamp(2, new Timestamp(f.getTime()));
                stmt.setTimestamp(3, new Timestamp(t.getTime()));
                stmt.setInt(4, (pageNumber - 1) * pageCount);
                stmt.setInt(5, pageCount);
            }else{
                stmt = con.prepareStatement("SELECT * FROM picture WHERE pic_type = ? and usr_id = ? and create_dt between ? and ? ORDER BY upload_dt DESC LIMIT ?,?");
                stmt.setInt(1, type);
                stmt.setString(2, uid);
                stmt.setTimestamp(3, new Timestamp(f.getTime()));
                stmt.setTimestamp(4, new Timestamp(t.getTime()));
                stmt.setInt(5, (pageNumber - 1) * pageCount);
                stmt.setInt(6, pageCount);
            }
            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                PictureVO adminVO = new PictureVO();
                adminVO.setUsrId(rs.getString("usr_id"));
                adminVO.setPicName(rs.getString("pic_name"));
                adminVO.setUploadDt(rs.getTimestamp("upload_dt"));
                adminVO.setPicType(rs.getInt("pic_type"));
                adminVO.setPicDesc(rs.getString("pic_desc"));
                adminVO.setSodDesc(rs.getString("sod_desc"));
                adminVO.setCreateDt(rs.getTimestamp("create_dt"));
                adminVO.setPicId(rs.getString("pic_id"));
                adminVO.setLat(rs.getDouble("lat"));
                adminVO.setLon(rs.getDouble("lon"));
                adminVO.setGznr(rs.getString("gznr"));
                adminVO.setClh(rs.getString("clh"));
                adminVO.setBw(rs.getInt("bw"));
                adminVO.setGzfj(rs.getInt("gzfj"));
                adminVO.setGzfl(rs.getInt("gzfl"));
                adminVO.setTw(rs.getInt("tw"));
                adminVO.setXc(rs.getInt("xc"));
                adminVOList.add(adminVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return adminVOList;
    }

    public List<PictureVO> countPictureGZFJ(String clh, String current, int type,  int xc, int tw, int bw, int gzfj) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<PictureVO> adminVOList = new ArrayList<PictureVO>();
        Date crt = Tools.parseDT2(current+" 00:00:00");
        crt = (crt == null ? new Date(): crt);
        Calendar cal = Calendar.getInstance();
        cal.setTime(crt);

        Date f = null, t = null;
        if(type == 1){ //周
            f = Tools.getFirstWeekDayOfTheGivingDate(crt);
            t = Tools.getLastWeekDayOfTheGivingDate(crt);
        }else if(type == 2){ //月
            f = Tools.getFirstMonthDayOfTheGivingDate(crt);
            t = Tools.getLastMonthDayOfTheGivingDate(crt);
        }else if(type == 3){ //季度
            f = Tools.getFirstSeasonDayOfTheGivingDate(crt);
            t = Tools.getLastSeasonDayOfTheGivingDate(crt);
        }else if(type == 4){ //年
            f = Tools.getFirstYearDayOfTheGivingDate(crt);
            t = Tools.getLastYearDayOfTheGivingDate(crt);
        }

        try {
            // 创建数据库连接
            con  = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            StringBuffer SQL = new StringBuffer();
            SQL.append("SELECT usr_id,clh,tw,bw,gzfj,count(*) as cnt FROM picture p WHERE 1=1 ");
            List<Object> params = new ArrayList<Object>();
            if(clh != null && !clh.trim().equals("")){
                SQL.append(" AND clh = ?  ");
                params.add(clh);
            }
            if(xc != -1){
                SQL.append(" AND xc = ? ");
                params.add(xc);
            }
            if(tw != -1){
                SQL.append(" AND tw = ? ");
                params.add(tw);
            }
            if(bw != -1){
                SQL.append(" AND bw = ? ");
                params.add(bw);
            }
            if(gzfj != -1){
                SQL.append(" AND gzfj = ? ");
                params.add(gzfj);
            }

            SQL.append(" AND create_dt between ? and ? AND gzfj is not null AND gzfl is not null group by usr_id,clh,tw,bw,gzfj");
            params.add(new Timestamp(f.getTime()));
            params.add(new Timestamp(t.getTime()));


            stmt = con.prepareStatement(SQL.toString());

            for(int i=0;i<params.size();i++){
                Object el = params.get(i);
                if(el instanceof Integer){
                    stmt.setInt(i+1, ((Integer)el).intValue());
                }
                if(el instanceof String){
                    stmt.setString(i + 1, String.valueOf(el));
                }
                if(el instanceof Timestamp){
                    stmt.setTimestamp(i+1, (java.sql.Timestamp)el);
                }
            }
            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                PictureVO adminVO = new PictureVO();
                adminVO.setUsrId(rs.getString("usr_id"));
                adminVO.setClh(rs.getString("clh"));
                adminVO.setTw(rs.getInt("tw"));
                adminVO.setBw(rs.getInt("bw"));
                adminVO.setGzfj(rs.getInt("gzfj"));
                adminVO.setGzfl(rs.getInt("cnt"));

                adminVOList.add(adminVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return adminVOList;
    }

    public List<PictureVO> countPictureGZFL(String clh, String current, int type,  int xc, int tw, int bw, int gzfl) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<PictureVO> adminVOList = new ArrayList<PictureVO>();
        Date crt = Tools.parseDT2(current+" 00:00:00");
        crt = (crt == null ? new Date(): crt);
        Calendar cal = Calendar.getInstance();
        cal.setTime(crt);

        Date f = null, t = null;
        if(type == 1){ //周
            f = Tools.getFirstWeekDayOfTheGivingDate(crt);
            t = Tools.getLastWeekDayOfTheGivingDate(crt);
        }else if(type == 2){ //月
            f = Tools.getFirstMonthDayOfTheGivingDate(crt);
            t = Tools.getLastMonthDayOfTheGivingDate(crt);
        }else if(type == 3){ //季度
            f = Tools.getFirstSeasonDayOfTheGivingDate(crt);
            t = Tools.getLastSeasonDayOfTheGivingDate(crt);
        }else if(type == 4){ //年
            f = Tools.getFirstYearDayOfTheGivingDate(crt);
            t = Tools.getLastYearDayOfTheGivingDate(crt);
        }

        try {
            // 创建数据库连接
            con  = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            StringBuffer SQL = new StringBuffer();
            SQL.append("SELECT usr_id,clh,tw,bw,gzfl,count(*) as cnt FROM picture p WHERE 1=1 ");
            List<Object> params = new ArrayList<Object>();
            if(clh != null && !clh.trim().equals("")){
                SQL.append(" AND clh = ?  ");
                params.add(clh);
            }
            if(xc != -1){
                SQL.append(" AND xc = ? ");
                params.add(xc);
            }
            if(tw != -1){
                SQL.append(" AND tw = ? ");
                params.add(tw);
            }
            if(bw != -1){
                SQL.append(" AND bw = ? ");
                params.add(bw);
            }

            if(gzfl != -1){
                SQL.append(" AND gzfl = ? ");
                params.add(gzfl);
            }
            SQL.append(" AND create_dt between ? and ? AND gzfj is not null AND gzfl is not null group by usr_id,clh,tw,bw,gzfl");
            params.add(new Timestamp(f.getTime()));
            params.add(new Timestamp(t.getTime()));


            stmt = con.prepareStatement(SQL.toString());

            for(int i=0;i<params.size();i++){
                Object el = params.get(i);
                if(el instanceof Integer){
                    stmt.setInt(i+1, ((Integer)el).intValue());
                }
                if(el instanceof String){
                    stmt.setString(i + 1, String.valueOf(el));
                }
                if(el instanceof Timestamp){
                    stmt.setTimestamp(i+1, (java.sql.Timestamp)el);
                }
            }
            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                PictureVO adminVO = new PictureVO();
                adminVO.setUsrId(rs.getString("usr_id"));
                adminVO.setClh(rs.getString("clh"));
                adminVO.setTw(rs.getInt("tw"));
                adminVO.setBw(rs.getInt("bw"));
                adminVO.setGzfl(rs.getInt("gzfl"));
                adminVO.setGzfj(rs.getInt("cnt"));

                adminVOList.add(adminVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return adminVOList;
    }

    public List<PictureVO> listPicture(String clh, String from, String to, int xc, int tw, int bw, int gzfj, int gzfl, int pageCount, int pageNumber) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<PictureVO> adminVOList = new ArrayList<PictureVO>();
        Date f = Tools.parseDT2(from+" 00:00:00");
        f = (f == null ? Tools.parseDT("1997-12-31"): f);
        Date t = Tools.parseDT2(to+" 23:59:59");
        t = (t == null ? Tools.parseDT("2017-12-31"): t);
        try {
            // 创建数据库连接
            con  = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            StringBuffer SQL = new StringBuffer();
            SQL.append("SELECT * FROM picture WHERE 1=1 ");
            List<Object> params = new ArrayList<Object>();
            if(clh != null && !clh.trim().equals("")){
                SQL.append(" AND clh = ?  ");
                params.add(clh);
            }
            if(xc != -1){
                SQL.append(" AND xc = ? ");
                params.add(xc);
            }
            if(tw != -1){
                SQL.append(" AND tw = ? ");
                params.add(tw);
            }
            if(bw != -1){
                SQL.append(" AND bw = ? ");
                params.add(bw);
            }
            if(gzfj != -1){
                SQL.append(" AND gzfj = ? ");
                params.add(gzfj);
            }
            if(gzfl != -1){
                SQL.append(" AND gzfl = ? ");
                params.add(gzfl);
            }
            SQL.append(" AND create_dt between ? and ? AND gzfj is not null AND gzfl is not null ORDER BY create_dt DESC LIMIT ?,?");
            params.add(new Timestamp(f.getTime()));
            params.add(new Timestamp(t.getTime()));
            params.add((pageNumber - 1) * pageCount);
            params.add(pageCount);

            stmt = con.prepareStatement(SQL.toString());

            for(int i=0;i<params.size();i++){
                Object el = params.get(i);
                if(el instanceof Integer){
                    stmt.setInt(i+1, ((Integer)el).intValue());
                }
                if(el instanceof String){
                    stmt.setString(i + 1, String.valueOf(el));
                }
                if(el instanceof Timestamp){
                    stmt.setTimestamp(i+1, (java.sql.Timestamp)el);
                }
            }
            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                PictureVO adminVO = new PictureVO();
                adminVO.setUsrId(rs.getString("usr_id"));
                adminVO.setPicName(rs.getString("pic_name"));
                adminVO.setUploadDt(rs.getTimestamp("upload_dt"));
                adminVO.setPicType(rs.getInt("pic_type"));
                adminVO.setPicDesc(rs.getString("pic_desc"));
                adminVO.setSodDesc(rs.getString("sod_desc"));
                adminVO.setCreateDt(rs.getTimestamp("create_dt"));
                adminVO.setPicId(rs.getString("pic_id"));
                adminVO.setLat(rs.getDouble("lat"));
                adminVO.setLon(rs.getDouble("lon"));
                adminVO.setGznr(rs.getString("gznr"));
                adminVO.setClh(rs.getString("clh"));
                adminVO.setBw(rs.getInt("bw"));
                adminVO.setGzfj(rs.getInt("gzfj"));
                adminVO.setGzfl(rs.getInt("gzfl"));
                adminVO.setTw(rs.getInt("tw"));
                adminVO.setXc(rs.getInt("xc"));
                adminVOList.add(adminVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return adminVOList;
    }

    public int listPictureCnt(String clh, String from, String to, int xc, int tw, int bw, int gzfj, int gzfl) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<PictureVO> adminVOList = new ArrayList<PictureVO>();
        Date f = Tools.parseDT2(from+" 00:00:00");
        f = (f == null ? Tools.parseDT("1997-12-31"): f);
        Date t = Tools.parseDT2(to+" 23:59:59");
        t = (t == null ? Tools.parseDT("2017-12-31"): t);
        try {
            // 创建数据库连接
            con  = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            StringBuffer SQL = new StringBuffer();
            SQL.append("SELECT count(*) FROM picture WHERE 1=1 ");
            List<Object> params = new ArrayList<Object>();
            if(clh != null && !clh.trim().equals("")){
                SQL.append(" AND clh = ?  ");
                params.add(clh);
            }
            if(xc != -1){
                SQL.append(" AND xc = ? ");
                params.add(xc);
            }
            if(tw != -1){
                SQL.append(" AND tw = ? ");
                params.add(tw);
            }
            if(bw != -1){
                SQL.append(" AND bw = ? ");
                params.add(bw);
            }
            if(gzfj != -1){
                SQL.append(" AND gzfj = ? ");
                params.add(gzfj);
            }
            if(gzfl != -1){
                SQL.append(" AND gzfl = ? ");
                params.add(gzfl);
            }
            SQL.append(" AND create_dt between ? and ? AND gzfj is not null AND gzfl is not null ");
            params.add(new Timestamp(f.getTime()));
            params.add(new Timestamp(t.getTime()));

            stmt = con.prepareStatement(SQL.toString());

            for(int i=0;i<params.size();i++){
                Object el = params.get(i);
                if(el instanceof Integer){
                    stmt.setInt(i+1, ((Integer)el).intValue());
                }
                if(el instanceof String){
                    stmt.setString(i + 1, String.valueOf(el));
                }
                if(el instanceof Timestamp){
                    stmt.setTimestamp(i+1, (java.sql.Timestamp)el);
                }
            }
            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return 0;
    }

    public int listPictureCnt(String uid, String from, String to, int type) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<PictureVO> adminVOList = new ArrayList<PictureVO>();
        Date f = Tools.parseDT2(from+" 00:00:00");
        f = (f == null ? Tools.parseDT("1997-12-31"): f);
        Date t = Tools.parseDT2(to+" 23:59:59");
        t = (t == null ? Tools.parseDT("2017-12-31"): t);
        System.out.println(from);
        System.out.println(to);
        try {
            // 创建数据库连接
            con  = DriverManager.getConnection(DBURL, DBUSER, DBPAS);

            // 创建数据库SQL执行对象, 此对象可以向数据库发送SQL语句
            if(uid == null || uid.trim().equals("")){
                stmt = con.prepareStatement("SELECT count(*) FROM picture WHERE pic_type = ? and create_dt between ? and ? ");
                stmt.setInt(1, type);
                stmt.setTimestamp(2, new Timestamp(f.getTime()));
                stmt.setTimestamp(3, new Timestamp(t.getTime()));
            }else{
                stmt = con.prepareStatement("SELECT count(*) FROM picture WHERE pic_type = ? and usr_id = ? and create_dt between ? and ? ");
                stmt.setInt(1, type);
                stmt.setString(2, uid);
                stmt.setTimestamp(3, new Timestamp(f.getTime()));
                stmt.setTimestamp(4, new Timestamp(t.getTime()));
            }
            // 执行查询，并获得结果对象
            rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }

            try {
                stmt.close();
            } catch (Exception ex) {
            }

            try {
                con.close();
            } catch (Exception ex) {
            }
        }
        return 0;
    }

}
