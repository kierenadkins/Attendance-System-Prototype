package sample;

import database.SQLiteDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import student.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.*;
import java.lang.annotation.Inherited;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    @FXML
    private AnchorPane LoginPage;

    @FXML
    private Button ClockIn;

    @FXML
    private Button ViewTimeTable;

    @FXML
    private Button ViewGrades;

    @FXML
    private Button AccountButton;

    @FXML
    private Button Campus;

    @FXML
    private Button StudentActivity;

    @FXML
    private Button StudentUnion;

    @FXML
    private Button VirtualTour;

    @FXML
    private AnchorPane AccountAnchorPane;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button ChangePasswordButton;

    @FXML
    private AnchorPane SigninPage;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField StudentId;

    @FXML
    private Button signInButtonOnAction;

    @FXML
    private Label signInLabel;

    @FXML
    private AnchorPane attendpage;

    @FXML
    private Button LeaveButton;

    @FXML
    private Button AttendButton;

    @FXML
    private Button XButton;

    @FXML
    private AnchorPane absentWindow;
    @FXML
    private ChoiceBox absentReason;
    @FXML
    private Button absentSubmit;
    @FXML
    private Button absentButton;

    @FXML
    private AnchorPane studentTabs;
    @FXML
    private AnchorPane teacherTabs;
    @FXML
    private AnchorPane homePage;
    @FXML
    private AnchorPane attendancePage;
    @FXML
    private Label absentName;
    @FXML
    private Label absentAttendance;
    @FXML
    private AnchorPane AdminPage;
    @FXML
    private Label absentReasonTeacher;
    @FXML
    private Label reasonID;
    @FXML
    private Label absentID;
    @FXML
    private TextField confirmID;
    @FXML
    private Button MainButton;
    @FXML
    private AnchorPane ResetStudent;
    @FXML
    private TextField StudentIdPassword;
    @FXML
    private TextArea OtherReason;
    @FXML
    private AnchorPane ResetStudent1;
    @FXML
    private AnchorPane noData;

    @FXML
    private TextField StudentIdPassword1;

    @FXML
    private Button changePassword1;

    @FXML
    private PasswordField CurrentPassword1;

    @FXML
    private PasswordField NewPassword;
    @FXML
    private AnchorPane absenceData;
    @FXML
    private Button x1;
    @FXML
    private AnchorPane ZoomLinkPage;
    @FXML
    private Button ZoomLink;
    @FXML
    private TextField CodeField;
    @FXML
    private Button confrimCodeButton;
    @FXML
    private Label Error;
    @FXML
    private Button Openfile;
    @FXML
    private Text attendanceP;

    @FXML
    private Button XButton1;




    ObservableList <String> choices = FXCollections.observableArrayList("Sick", "Late", "Medical", "Family", "Job" , "Commitment", "Other");
    

    @FXML
    public void Logout(ActionEvent event){
        SigninPage.toFront();
    }
    @FXML
    public void Checkcode(ActionEvent event){
        if (CodeField.getText().equals("123456")){
          ZoomLinkPage.toBack();
            Student student = loginInfo();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String x = dtf.format(now);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("attendance_info.csv", true));
                writer.write(x + ", ");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            LeaveButton.toFront();

        }
        else{
            CodeField.clear();
            Error.setText("this code is incorrect");
        }
    }
    @FXML
    public void changepasswordStudent(ActionEvent event){ResetStudent1.toFront();}
    @FXML
    public void changepasswordStudentOnAction(ActionEvent event){
        String id = StudentId.getText();
        String currentpassword = SQLiteDatabase.studentPassword(id);
        if (currentpassword.equals(CurrentPassword1.getText())){
            SQLiteDatabase.StudentUpdatePassword(id, NewPassword.getText());
        }
        else{
            System.out.println("wrong currentpassword");
        }
    }
    @FXML
    public void x(ActionEvent event){
        ResetStudent.toBack();
        ResetStudent1.toBack();
    }
    @FXML
    public void recordAbsent(ActionEvent event){
        absentReason.setItems(choices);
        absentWindow.toFront();
    }
    @FXML
    public void changepasswordStudentWindow(ActionEvent event){
        ResetStudent1.toFront();
    }
    @FXML
    public void backtomain(ActionEvent event) {
        LoginPage.toFront();

    }
    @FXML
    public void OpenFile(ActionEvent event) throws IOException {
        Desktop.getDesktop().open(new File("C:\\Users\\kiere\\Documents\\S\\attendance_info.csv")); //move else where later
    }

    public void Teacherchangepassword(ActionEvent event){
        String id = StudentIdPassword.getText();
        String name = SQLiteDatabase.studentFName(id);



        SQLiteDatabase.UpdatePassword(id, name);


    }
    @FXML
    public void closeWindow(ActionEvent event){
        AccountAnchorPane.toBack();
        attendancePage.toBack();

    }

    @FXML
    public void markAsRead(ActionEvent event){
        Student student = loginInfo();
        String filePath = "absentReasons.csv";
        String removeTerm = student.getStudentCourse();
        removeRecord(filePath, removeTerm, 4);
        confirmID.clear();
        String searchTerm = student.getStudentCourse();
        String[] studentData = (fetchStudentData(searchTerm, filePath));
        try{
            absentName.setText(studentData[1]);
            reasonID.setText(studentData[0]);
            absentAttendance.setText(studentData[2]);
            absentReasonTeacher.setText(studentData[4]);
            absentID.setText(studentData[5]);
        }
        catch (Exception var15){
            absentName.setText("");
            reasonID.setText("");
            absentAttendance.setText("");
            absentReasonTeacher.setText("");
            absentID.setText("");
            noData.toFront();
        }

    }

    public void removeRecord(String filePath, String removeTerm, int positionOfTerm) {
        int position = positionOfTerm - 1;
        String tempFile = "temp.csv";
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);

        try {
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String currentLine;
            while((currentLine = br.readLine()) != null) {
                String[] data = currentLine.split(",");
                String ID = data[position];
                String confirm = data[5];
                System.out.println(removeTerm);
                System.out.println(ID);
                System.out.println(confirm);
                System.out.println(confirmID.getText());

                if (!ID.equalsIgnoreCase(removeTerm) || !confirm.equals(confirmID.getText())) {
                    System.out.println("keep");
                    pw.println(currentLine);
                }
            }
            pw.flush();
            pw.close();
            fr.close();
            br.close();
            bw.close();
            fw.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);
        } catch (Exception var15) {
            var15.printStackTrace();
        }

    }

    public static String[] fetchStudentData(String searchTerm, String filePath){
        ArrayList<String> records = new ArrayList<String>();

        String ID = ""; String name = ""; String attendance = ""; String course = ""; String reason = ""; String reasonID = "";
        boolean found =false;

        try {
            Scanner x;
            x = new Scanner(new File(filePath));
            x.useDelimiter("[,\n]");

            while (x.hasNext() && !found){
                ID = x.next();
                name = x.next();
                attendance = x.next();
                course = x.next();
                reason = x.next();
                reasonID = x.next();

                if (course.equals(searchTerm)){
                    records.add(ID);
                    records.add(name);
                    records.add(attendance);
                    records.add(course);
                    records.add(reason);
                    records.add(reasonID);
                    found = true;

                }
            }
            x.close();
        }
        catch (Exception e){
            System.out.println("Error");
        }

        String[] studentData = new String[records.size()];
        records.toArray(studentData);
        return studentData;
    }

    public void viewAttendance(ActionEvent event){
        Student student = loginInfo();
        attendancePage.toFront();
        String searchTerm = student.getStudentCourse();
        String filePath = "absentReasons.csv";
        String[] studentData = (fetchStudentData(searchTerm, filePath));
        try{
            absentName.setText(studentData[1]);
            reasonID.setText(studentData[0]);
            absentAttendance.setText(studentData[2]);
            absentReasonTeacher.setText(studentData[4]);
            absentID.setText(studentData[5]);
        }
        catch (Exception var15){
            absentName.setText("");
            reasonID.setText("");
            absentAttendance.setText("");
            absentReasonTeacher.setText("");
            absentID.setText("");
            noData.toFront();
        }
    }


    public void submitAbsent(ActionEvent event){
        Student student = loginInfo();
        ArrayList<String> absentString = new ArrayList();
        absentString.add(student.getStudentID());
        absentString.add((student.getStudentFName()) + " " + (student.getStudentLName()));
        absentString.add(student.getStudentAttendance());
        absentString.add(student.getStudentCourse());

        String value = (String) absentReason.getValue();
        if (value.equals("Other")) {
            value = OtherReason.getText();
        }
        absentString.add(value);

        int randomNum = ThreadLocalRandom.current().nextInt(0, 100000 + 1);
        absentString.add(String.valueOf(randomNum));

        String absentList = String.join(",", absentString);
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("absentReasons.csv", true));
            writer.write(absentList);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        absentWindow.toBack();
        OtherReason.clear();
    }

    public void signInButtonOnAction(ActionEvent event){
        if (StudentId.getText().isEmpty() || Password.getText().isEmpty()){
            signInLabel.setText("Enter a Password and Email");
        }
        else{
            String studentID = StudentId.getText();
            String studentPassword = Password.getText();
            System.out.println("Welcome," + studentID);
            validateLogin(studentID, studentPassword);
        }
    }

    public void validateLogin(String studentID, String studentPassword) {
        boolean validate = SQLiteDatabase.verifyLogin(studentID, studentPassword);
        if(validate) {
            SigninPage.toBack();
            LoginPage.toFront();
            //homePage.toFront();
            Student student = loginInfo();
            if(student.getStudentStatus().equals("Teacher")){
                AdminPage.toFront();
                teacherTabs.toFront();

                String searchTerm = student.getStudentCourse();
                String filePath = "absentReasons.csv";
                String[] studentData = (fetchStudentData(searchTerm, filePath));
                try{
                    absenceData.toFront();
                    absentName.setText(studentData[1]);
                    reasonID.setText(studentData[0]);
                    absentAttendance.setText(studentData[2]);
                    absentReasonTeacher.setText(studentData[4]);
                    absentID.setText(studentData[5]);
                }
                catch (Exception var15){
                    absentName.setText("");
                    reasonID.setText("");
                    absentAttendance.setText("");
                    absentReasonTeacher.setText("");
                    absentID.setText("");
                    noData.toFront();
                }
            }
            else {
                studentTabs.toFront();
                attendanceP.setText(student.getStudentAttendance());
            }
        }
    }

    @FXML
    public void teacherStudentReset(ActionEvent event){
        ResetStudent.toFront();
    }

    public void checkdetails(){
        SigninPage.toBack();
        LoginPage.toFront();
    }

    @FXML
    void Account (ActionEvent event){
        AccountAnchorPane.toFront();

    }
    @FXML
    void ZoomLinkForward(ActionEvent event){
        ZoomLinkPage.toFront();
    }


    @FXML
    void RecordAttendance(ActionEvent event){
        Student student = loginInfo();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String x = dtf.format(now);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("attendance_info.csv", true));
            writer.write(x + ", ");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LeaveButton.toFront();
    }

    public Student loginInfo() {
        String studentID = StudentId.getText();
        String studentFName = SQLiteDatabase.studentFName(studentID);
        String studentLName = SQLiteDatabase.studentLName(studentID);
        String studentPassword = SQLiteDatabase.studentPassword(studentID);
        String studentCourse = SQLiteDatabase.studentCourse(studentID);
        String studentAttendance = SQLiteDatabase.studentAttendance(studentID);
        String studentStatus = SQLiteDatabase.studentStatus(studentID);

        return new Student(studentID, studentFName, studentLName, studentPassword, studentCourse, studentAttendance, studentStatus);
    }
    public String timeleft(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String x = dtf.format(now);
        return x;
    }
    @FXML
    void RecordTimeLeft(ActionEvent event){

        String x = timeleft();
        String studentID = StudentId.getText();
        String studentFName = SQLiteDatabase.studentFName(studentID);
        String studentLName = SQLiteDatabase.studentLName(studentID);
        String studentCourse = SQLiteDatabase.studentCourse(studentID);
        String studentAttendance = SQLiteDatabase.studentAttendance(studentID);



        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("attendance_info.csv", true));
            writer.write(studentID + ", " + studentFName +", " + studentLName + ", " + studentAttendance+", " + studentCourse + "," + x);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttendButton.toFront();
    }

    @FXML
    void Attendpage(ActionEvent event){
        Student student = loginInfo();
        System.out.println(student.getStudentStatus());
        attendpage.toFront();
    }

    @FXML
    void UniversityLogin (ActionEvent event){
        try {

            URI Campus= new URI("https://www.bue.edu.eg/pages/login-page/");
            java.awt.Desktop.getDesktop().browse(Campus);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    @FXML
    void CampusMap (ActionEvent event){
        try {

            URI Campus= new URI("https://www.bue.edu.eg/campus-map/");

            java.awt.Desktop.getDesktop().browse(Campus);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    void StudentUnionWebsite (ActionEvent event){
        try {

            URI Union= new URI("https://www.bue.edu.eg/student-activities-final/#1535914711582-d7b3101d-fe08");

            java.awt.Desktop.getDesktop().browse(Union);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    void StudentActivityWebsite (ActionEvent event){
        try {

            URI Activity= new URI("https://www.bue.edu.eg/student-activities-final/");

            java.awt.Desktop.getDesktop().browse(Activity);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    void VirtualTourWebsite (ActionEvent event){
        try {

            URI Campus= new URI("https://www.bue.edu.eg/virtual-tour/");

            java.awt.Desktop.getDesktop().browse(Campus);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
