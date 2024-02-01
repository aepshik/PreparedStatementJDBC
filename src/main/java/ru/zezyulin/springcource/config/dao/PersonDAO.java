package ru.zezyulin.springcource.config.dao;

import jdk.vm.ci.meta.SpeculationLog;
import org.springframework.stereotype.Component;
import ru.zezyulin.springcource.config.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private static final String URL="jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME="postgres";
    private static final String PASSWORD="зщыепкуы";
    private static Connection connection;
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL="SELECT * FROM Person";
            ResultSet resultSet=statement.executeQuery(SQL);
            while (resultSet.next()){
                Person person=new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));
                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }
    public Person show(int id){
        Person person=null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("SELECT * from PERSON where id=?");
       preparedStatement.setInt(1,id);
       ResultSet resultSet=preparedStatement.executeQuery();
       resultSet.next();
       person=new Person();
       person.setId(resultSet.getInt("id"));
       person.setName(resultSet.getString("name"));
       person.setEmail(resultSet.getString("email"));
       person.setAge(resultSet.getInt("age"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //    return people.stream().filter(person->person.getId()==id).findAny().orElse(null);
    return null;
    }
    public void save(Person person){
        //person.setId(++PEOPLE_COUNT);
        //people.add(person);
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("INSERT INTO Person VALUES(1, ?, ?, ?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(int id, Person updatePerson){
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? where id=?");
            preparedStatement.setString(1, updatePerson.getName());
            preparedStatement.setString(2, updatePerson.getEmail());
            preparedStatement.setInt(3, updatePerson.getAge());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //  Person personToBeUpdated=show(id);
        //personToBeUpdated.setName(updatePerson.getName());
        //personToBeUpdated.setAge(updatePerson.getAge());
        //personToBeUpdated.setEmail(updatePerson.getEmail());
    }
    public void delete(int id){
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("DELETE from Person where id=?");
       preparedStatement.setInt(1,id);
       preparedStatement.executeUpdate();
        } catch (SQLException e) {

        }

        // people.removeIf(p->p.getId()==id);
    }
}
