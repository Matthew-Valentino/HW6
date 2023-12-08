
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.Author;
import model.Paper;
import model.Participant;
import mapper.ParticipantMapper;
import mapper.PaperMapper;
public class ConferenceDb {
    private SqlSessionFactory factory;

    public ConferenceDb() throws IOException {
        String resource = "com/yourpackage/resources/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.factory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    public static void main(String[] args) {
        try {
            ConferenceDb conferenceDb = new ConferenceDb();
            conferenceDb.startMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("MENU\n****************************************************");
            System.out.println("(1) Print the list of papers whose average overall Recommendation is more than 7");
            System.out.println("(2) Enter Participator Details");
            System.out.println("(3) Enter Author Details");
            System.out.println("(4) Exit");
            System.out.println("****************************************************");
            System.out.print("Please make your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    printPapers();
                    break;
                case 2:
                    enterParticipatorDetails(scanner);
                    break;
                case 3:
                    enterAuthorDetails(scanner);
                    break;
                case 4:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 4);

        scanner.close();
    }

    private void printPapers() {
        try (SqlSession session = factory.openSession()) {
            List<Paper> papers = session.selectList("com.yourdomain.mapper.PaperMapper.listPapersWithHighRecommendation");
            for (Paper paper : papers) {
                System.out.println(paper.getTitle() + " (" + paper.getId() + ")");
            }
        }
    }

    private void enterParticipatorDetails(Scanner scanner) {
        System.out.print("Please enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Please enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Please enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Please enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Please enter Affiliation: ");
        String affiliation = scanner.nextLine();

        Participant participant = new Participant(email, firstName, lastName, phone, affiliation);
        
        try (SqlSession session = factory.openSession()) {
            session.insert("com.yourdomain.mapper.ParticipantMapper.insertParticipant", participant);
            session.commit();
            System.out.println("Participator Record inserted successfully");
        } catch (PersistenceException e) {
            System.out.println("Error: " + e.getMessage());
            // Handle specific database constraint violations if needed
        }
    }

    private void enterAuthorDetails(Scanner scanner) {
        // List existing participants if needed
        try (SqlSession session = factory.openSession()) {
            List<Participant> participants = session.selectList("com.yourdomain.mapper.ParticipantMapper.listParticipants");
            for (Participant participant : participants) {
                System.out.println(participant.getFirstName() + " (" + participant.getEmail() + ")");
            }
        }

        // Prompt for author details
        System.out.print("Please enter Email for the Conference Author: ");
        String email = scanner.nextLine();
        System.out.print("Please enter First Name for the Conference Author: ");
        String firstName = scanner.nextLine();
        System.out.print("Please enter Last Name for the Conference Author: ");
        String lastName = scanner.nextLine();
        System.out.print("Please enter Phone for the Conference Author: ");
        String phone = scanner.nextLine();
        System.out.print("Please enter Affiliation for the Conference Author: ");
        String affiliation = scanner.nextLine();

        // Create an Author instance with the gathered details
        Author author = new Author(email, firstName, lastName, phone, affiliation);

        // Insert the new author record
        try (SqlSession session = factory.openSession()) {
            session.insert("com.yourdomain.mapper.AuthorMapper.insertAuthor", author);
            session.commit();
            System.out.println("Inserted Author Record Successfully");
        } catch (PersistenceException e) {
            System.out.println("Error: " + e.getMessage());
            // Handle specific database constraint violations if needed
        }
    }
}