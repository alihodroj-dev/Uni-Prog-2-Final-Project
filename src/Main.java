public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        data.loadData();
        data.saveData();

        for(Student s : data.getStudents()) {
            System.out.println(s.getUsername());
            for(Class c : s.getClassesEnrolledIn()) {
                for(Question q : c.getQuiz().getQuestions()) {
                    System.out.println("Question: " + q.getDescription());
                    for(String option : q.getOptions()) {
                        System.out.println((q.getOptions().indexOf(option) + 1) + ") " + option);
                    }
                }
            }
        }

    }
}