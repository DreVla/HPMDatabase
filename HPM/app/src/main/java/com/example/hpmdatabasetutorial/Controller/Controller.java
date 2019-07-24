package com.example.hpmdatabasetutorial.Controller;

public class Controller {


//    public void loadStudents(View view) {
//        spinnerPos = spinner.getSelectedItemPosition();
//        Log.d("Position", String.valueOf(spinnerPos));
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        lst.setText(dbHandler.loadHandler(spinnerPos));
//        personId.setText("");
//        personName.setText("");
//        spinner.getSelectedItemPosition();
//    }
//
//
//    public void addStudent(View view) {
//        spinnerPos = spinner.getSelectedItemPosition();
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        int id = Integer.parseInt(personId.getText().toString());
//        String name = personName.getText().toString();
//        Person person = new Person(id, name);
//        dbHandler.addHandler(person, spinnerPos);
//        personId.setText("");
//        personName.setText("");
//    }
//
//
//    public void findStudent(View view) {
//        spinnerPos = spinner.getSelectedItemPosition();
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        Person person =
//                dbHandler.findHandler(personName.getText().toString(), spinnerPos);
//        if (person != null) {
//            lst.setText(String.valueOf(person.getId()) + " " + person.getName() + System.getProperty("line.separator"));
//            personId.setText("");
//            personName.setText("");
//        } else {
//            lst.setText("No Match Found");
//            personId.setText("");
//            personName.setText("");
//        }
//    }
//
//
//    public void removeStudent(View view) {
//        MyDBHandler dbHandler = new MyDBHandler(this, null,
//                null, 1);
//        boolean result = dbHandler.deleteHandler(Integer.parseInt(
//                personId.getText().toString()), spinnerPos);
//        if (result) {
//            personId.setText("");
//            personName.setText("");
//            lst.setText("Record Deleted");
//        } else
//            personId.setText("No Match Found");
//    }
//
//
//    public void updateStudent(View view) {
//        MyDBHandler dbHandler = new MyDBHandler(this, null,
//                null, 1);
//        boolean result = dbHandler.updateHandler(Integer.parseInt(
//                personId.getText().toString()), personName.getText().toString(), spinnerPos);
//        if (result) {
//            personId.setText("");
//            personName.setText("");
//            lst.setText("Record Updated");
//        } else
//            personId.setText("No Match Found");
//    }
//
//
//    public void seeStudents(View view) {
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        lst.setText(dbHandler.loadTeacherStudents(Integer.parseInt(personId.getText().toString())));
//        personId.setText("");
//        personName.setText("");
//    }
//
//    public void assignStudentToTeacher(View view) {
//        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
//        int idStudent = Integer.parseInt(personName.getText().toString());
//        int idTeacher = Integer.parseInt(personId.getText().toString());
//        boolean checkIfAssigned = dbHandler.assignStudent(idStudent, idTeacher);
//        if(checkIfAssigned){
//            Toast.makeText(this, "Student assigned succesfully!", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//        }
//        personId.setText("");
//        personName.setText("");
//    }
}
