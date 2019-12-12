package com.uniyaz;

import java.math.BigDecimal;
import java.sql.*;

public class DBConnection {
    final static String JDBC_CONNECTION_STR = "jdbc:mysql://127.0.0.1:3306/marvel?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    final static String USERNAME = "root";
    final static String PASSWORD = "12345";

    boolean IsConnected() {
        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD)) {

            if (conn != null) {
                return true;
            } else {
                System.out.println("Fail to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    void heroEkle(Hero hero) {
        String sql = "insert into hero (name, surname)" +
                "values (?, ?) ";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, hero.getName());
            preparedStatement.setString(2, hero.getSurname());
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır eklendi.");

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void movieEkle(int heroId, Movie movie) {
        String sql = "insert into movie (id_hero, movie, budget)" +
                "values (?, ?, ?) ";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, heroId);
            preparedStatement.setString(2, movie.getMovie());
            preparedStatement.setBigDecimal(3, movie.getBudget());
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır eklendi.");

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void heroListele() {
        String sql = "Select * from hero";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Hero ID  |  Name  |  Surname ");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String adi = resultSet.getString("name");
                String soyadi = resultSet.getString("surname");

                System.out.printf("%d | %s | %s \n", id, adi, soyadi);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void movieListele() {
        String sql = "Select * from movie";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Hero ID  |  Movie Name   |  Budget");
            while (resultSet.next()) {
                int heroId = resultSet.getInt("id_hero");
                String movie = resultSet.getString("movie");
                BigDecimal budget = resultSet.getBigDecimal("budget");

                System.out.printf(" %d | %s | %s \n", heroId, movie, budget);
            }
            System.out.println("------------------------------------------");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void heroToplamButceHesapla() {
        System.out.println("-----Hero total budget------");
        String sql = "select concat(h.name, h.surname) as hero, ifnull(sum(budget), 0) as total_budget " +
                "from hero h left join movie m on h.id = m.id_hero " +
                "group by m.id_hero " +
                "order by total_budget desc";

        try(Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(sql))  {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
            String hero = resultSet.getString("hero");
            BigDecimal totalBudget = resultSet.getBigDecimal("total_budget");

                System.out.printf("%s        |    %f \n ", hero, totalBudget);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------");
    }

    void heroFilmSayısıHesapla() {
        System.out.println("-------Hero moive count--------");
        String sql = "select concat(h.name, h.surname) as hero, count(m.movie) as movie_count " +
                "from hero h inner join movie m on m.id_hero = h.id " +
                "group by m.id_hero " +
                "order by movie_count desc";

        try(Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(sql))  {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String hero = resultSet.getString("hero");
                int movieCount = resultSet.getInt("movie_count");

                System.out.printf("%s        |    %d \n ", hero, movieCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------");
    }

}
