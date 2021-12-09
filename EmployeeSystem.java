import java.util.*;

public class EmployeeSystem {
	public static void main(String [] args) {
		EmployeeSystem database = new EmployeeSystem();
		Employee[] employees = database.initialise(); // Initialisera
		Scanner keyboard = new Scanner(System.in); // Skapa Scanner objekt
		String choice = "";

		// Meny alternativ
		
		System.out.println("Welcome to the Employee System!");
		System.out.println("These are your options:");
		System.out.println("a: Add employee to system."); 
		System.out.println("b: Update title based on ID.");
		System.out.println("c: Remove employee based on ID.");
		System.out.println("d: Show info about searched employee.");
		System.out.println("e: Calculate wage for one weeks work.");
		System.out.println("f: Show employees by searched title.");
		System.out.println("g: Employee count by title.");
		System.out.println("h: Show all employees.");
		System.out.println("i: Show employees below wage.");
		System.out.println("j: Give random day off.");
		System.out.println("k: Sort all employees by title.");
		System.out.println("q: Quit.");
		System.out.println("Enter a letter from a-k or q to quit.");

		// Användarinput
		
		while (!choice.equals("q") && !choice.equals("Q")) { // Medans valet inte är "q" gör något annat
			choice = database.getString(keyboard);

			System.out.println("You selected option " + choice); // Visar användarens val

			if(choice.toLowerCase().equals("a")) { // Val för användaren att lägga till en anställd i systemet
				database.addEmployeetoArray(employees, database.addEmployee(keyboard));

			}
			else if (choice.toLowerCase().equals("b")) { // Uppdatera titel hos en anställd
				Employee employee = database.searchEmployeeID(employees, keyboard);
				if(employee != null) {
					employee = database.updateTitle(employee, keyboard);
				}

			}
			else if (choice.toLowerCase().equals("c")) { // Ta bort anställd via employeeID
				database.deleteEmployee(employees, keyboard);

			}

			else if (choice.toLowerCase().equals("d")) { // Visa information om anställd
				Employee employee = database.searchEmployeeID(employees, keyboard);
				if(employee != null) employee.printInfo();

			}

			else if (choice.toLowerCase().equals("e")) { // Beräkna och visa lön/v för sökt anställd
				Employee employee = database.searchEmployeeID(employees, keyboard);
				if(employee != null) database.weeklyPay(employee);

			}

			else if (choice.toLowerCase().equals("f")) { // Lista alla anställda med sökt titel
				System.out.println("Enter title: ");
				database.searchArbetstitel(employees, keyboard);

			}

			else if (choice.toLowerCase().equals("g")) { // Summa av antalet anställda baserat på titel
				System.out.println("Enter title: ");
				System.out.println(database.countArbetstitel(employees, keyboard));

			}

			else if (choice.toLowerCase().equals("h")) { // Lista alla anställda
				database.printAll(employees);

			}

			else if (choice.toLowerCase().equals("i")) { // Sök efter anställda med lägre lön än angivet
				System.out.println("Enter wage: ");
				database.lowerWages(employees, keyboard);
				keyboard.nextLine();

			}

			else if (choice.toLowerCase().equals("j")) { // Slumpa fram en anställd som vinner en ledig dag	
				Employee[] tempEmployees = database.getAboveHours(employees);
				Employee tempEmployee = database.ledigDag(tempEmployees);
				database.updateEmployee(employees, tempEmployee);
				System.out.println("Day off given to:");
				tempEmployee.printInfo();

			}

			else if (choice.toLowerCase().equals("k")) { // Sorterar alla anställda via titel
				database.sortArbetstitel(employees);
				database.printAll(employees);

			}

			else if (choice.toLowerCase().equals("q")) { // Stäng programmet
				System.out.println("Goodbye!");
				System.exit(0);
			}

			else {
				System.out.println("Invalid option.");
			}
			System.out.println("Would u like to do something else? \nEnter a letter from a-k or q to quit."); // Frågar om användaren vill göra något annat/mer
		}
	}

	
	// a. Lägg till anställd
	/* 
	 * @Param Scanner för användar input
	 * @Return/Få tillbaka anställd
	 * Ber om information om den nya anställda via frågor
	 */
	
	public Employee addEmployee(Scanner keyboard) {

		Employee employee = new Employee(); // Skapa employee objekt
		System.out.println("Enter employee ID: "); // Lägg till anställnings ID
		String id = getString(keyboard);
		employee.setEmployeeID(id);

		System.out.println("Enter title: "); // Lägg till arbetstitel
		String title = getString(keyboard);
		employee.setArbetstitel(title);

		System.out.println("Enter Firstname: "); // Lägg till förnamn
		String firstname = getString(keyboard);
		employee.setFnamn(firstname);

		System.out.println("Enter Lastname: "); // Lägg till efternamn
		String lastname = getString(keyboard);
		employee.setEnamn(lastname);

		System.out.println("Enter Age: "); // Lägg till ålder
		int age = getInt(keyboard);
		employee.setAlder(age);

		System.out.println("Enter Pay: "); // Lägg till lön
		double pay = getDouble(keyboard);
		employee.setLonPerTim(pay);

		System.out.println("Enter workhours: "); // Lägg till arbetstimmar
		double workhours = getDouble(keyboard);
		employee.setTimVecka(workhours);

		keyboard.nextLine();
		System.out.println("Enter City: "); // Lägg till stad
		String city = getString(keyboard);
		employee.setStad(city);		

		return employee; // Få tillbaka employee objekt
	}


	/*
	 * @Param Employee[] en array av Employees som innehåller objekten employee; newEmployee är ett Employee objekt som kommer läggas till till arrayen av Employees
	 * Lägg till employee till arrayen ifall person med samma ID inte redan finns 
	 */
	
	public void addEmployeetoArray(Employee employees[], Employee newEmployee) {
		for(int i = 0; i< employees.length; i++) { // Loopa genom arrayen av employees till slutet
			if(employees[i] == null) { // Null check 
				employees[i] = newEmployee; // newEmployee tillagd till arrayen
				System.out.println(employees[i].employeeID + " added");
				return;
			}// Meddelande ifall person redan finns
			if(employees[i].employeeID.equals(newEmployee.employeeID)) { // Kollar om person med samma ID redan finns med i arrayen
				System.out.println("A person with this ID is already in your system");
				return; 
			}
		}
	}

	/*
	 * @Param Employee[] en array av Employees som innehåller objekten Employee, Scanner för användar input
	 * @Return få tillbaka den sökta anställda
	 * Söker efter Employee i Employee arrayen
	 */
	
	public Employee searchEmployeeID(Employee employees[], Scanner keyboard) {
		System.out.println("Enter employeeID:");
		String employeeID = getString(keyboard); // Sök person via employeeID
		for(int i = 0; i < employees.length; i++) {
			if(employees[i] == null ) { 
				System.out.println("Employee does not exist");
				return null;
			}
			if(employeeID.equals(employees[i].employeeID)) {
				return employees[i];
			}
		}
		System.out.println("Employee does not exist");
		return null;
	}

	
	// b. Updatera arbetstitel baserat på employeeID
	/*
	 * @Param Employee[] en array av Employees som innehåller objekt employee
	 * Kolla om Employee är i arrayen och uppdatera informationen
	 */
	
	public void updateEmployee(Employee employees[], Employee employee) {

		for(int i = 0; i < employees.length; i++) {
			if(employees[i] == null) { 
				System.out.println("Employee does not exist");
				return;
			}
			if(employee.employeeID.equals(employees[i].employeeID)) { // Ifall anställd hittas uppdatera anställd
				employees[i] = employee;
				return;
			}
		}
	}

	/*
	 * @Param Employee[] en array av Employees som innehåller objekten Employee, Scanner för användar input
	 * @Return/Få tillbaka uppdaterad anställd
	 * Uppdaterar Employee i Employee arrayen
	 */
	
	public Employee updateTitle(Employee employee, Scanner keyboard) {
		System.out.println("What is the new tite of the employee"); // Lägg in ny titel
		String newArbetstitel = getString(keyboard);
		employee.setArbetstitel(newArbetstitel);
		return employee;
	}	

	
	// c. Ta bort anställd baserat på employeeID
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee, Scanner för användar input
	 * Ta bort anställd om sökt anställd fanns med i arrayen
	 */
	
	public void deleteEmployee(Employee employees[], Scanner keyboard) {
		Employee deleteEmployee = searchEmployeeID(employees, keyboard);
		if(deleteEmployee == null) return;
		else {
			for(int i = 0; i<employees.length; i++) {
				if(employees[i].employeeID.equals(deleteEmployee.employeeID)) { // Kolla om ID matchar i arrayen
					System.out.println(employees[i].employeeID + " was removed");
					employees[i] = null; 
					moveEmployee(employees, i);
					return;
				}
			}
		}
	}
	
	// Flyttar Employees position till vänster
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee
	 */
	
	public void moveEmployee(Employee employees[], int i) {
		while(i<employees.length) {
			if(employees[i+1] == null) break;
			employees[i] = employees[i+1];
			employees[i+1] = null;
			i++;
		}
	}


	// e. Räkna ut lönen för en veckas arbete
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee
	 * Kalkulerar veckolönen och visar användar ID, för- och efternamn samt veckolön i konsollen
	 */
	
	public void weeklyPay(Employee employee) {
		System.out.println("Employee ID: " + employee.employeeID);
		System.out.println("Namn: " + employee.fnamn + " " + employee.enamn);
		System.out.println("Lön: " + (employee.lonPerTim*employee.timVecka));  // Timlön gånger antalet arbetstimmar

	}

	// f. Lista alla arbetare med sökt titel
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee, Scanner för användar input
	 * Söker och visar alla anställda under angiven titel
	 */

	public void searchArbetstitel(Employee employees[], Scanner keyboard) {
		String arbetstitel = getString(keyboard);
		int count = 0;
		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) {
				if(count == 0) System.out.println("No employee has this title");
				return;
			}
			if(employees[i].arbetstitel.equals(arbetstitel)) {
				employees[i].printInfo();
				count++;
			}
		}
	}

	// g. Räkna alla med samma arbetstitel
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee, Scanner för användar input
	 * @Return Antalet med samma arbetstitel
	 * Räknar alla anställda med samma titel
	 */
	
	public int countArbetstitel(Employee employees[], Scanner keyboard) {
		String arbetstitel = getString(keyboard);
		int count = 0;
		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) break;
			if(employees[i].arbetstitel.equals(arbetstitel)) {
				count++;
			}
		}
		return count;
	} 


	// h. Info om alla arbetare
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee
	 * Visa information om alla anställda i konsollen
	 */

	public void printAll(Employee[] employees) {

		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) break;

			employees[i].printInfo();
		}
	}


	// i. Sök under specifik lön
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee, Scanner för användar input
	 * Visa alla anställda med en lön lägre än vad som angivits
	 */

	public void lowerWages(Employee[] employees, Scanner keyboard) {
		double wage = getDouble(keyboard);
		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) break;
			if(employees[i].lonPerTim<wage) {
				System.out.println("Name: " + employees[i].fnamn + " " + employees[i].enamn);

			}
		}
	}
	
	// j. Anställd över 30h/vecka
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee
	 * @Return array av anställda med mer än 30h/vecka
	 * Skapar en array med anställda som jobbar mer än 30h/vecka
	 */
	
	public Employee[] getAboveHours(Employee[] employees) {
		Employee[] aboveEmployee = new Employee[20];
		int counter = 0;
		for(int i = 0; i<employees.length; i++) {
			if(employees[i] == null) break;
			if(employees[i].timVecka>30) {
				aboveEmployee[counter] = employees[i];
				counter++;
			}
		}
		return aboveEmployee;
	}

	// Random person
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee
	 * @Return person som får en ledig dag
	 * Slumpar ut en anställd som kan få en ledig dag baserat på att jobba minst 30h/vecka
	 */
	
	public Employee ledigDag(Employee[] employees) {
		Random r = new Random();
		int counter = 0;
		for(int i = 0; i< employees.length; i++) {
			if(employees[i] == null) break;
			counter++;
		}
		int ledigDag = r.nextInt(counter);
		employees[ledigDag].setLedigDag(true);
		return employees[ledigDag];
	}


	// k. Sortera baserat på arbetstitel
	/*
	 * @Param Employee [] en array av Employees som innehåller objekten Employee
	 * Sorterar alla anställda baserat på arbetstitel
	 */
	
	public void sortArbetstitel(Employee[] employees){
		Employee temp;
		boolean sorted = false;
		while(!sorted) {
			sorted = true;
			for(int i = 0; i<employees.length; i++) {
				if(employees[i] == null) break;
				if(employees[i+1] == null) break;
				if(0 <(employees[i].arbetstitel).toLowerCase().compareTo(employees[i+1].arbetstitel.toLowerCase())) {
					temp = employees[i]; // Håller temp
					employees[i] = employees[i+1]; // Byt till vänster
					employees[i+1] = temp; // Byt till höger
					sorted = false;
				}
			}
		}
	}
	
	/*
	 * @Return En array med anställda som redan initialiserats
	 * Initialiserar start/förbestämd data för anställda
	 */
	
	public Employee[] initialise() {
		Employee[] employees = new Employee[20];
		employees[0] = new Employee("E1", "Snickare", "Hanna", "Bell", 22, 180, 40, "Falun");
		employees[1] = new Employee("E2", "Systemutvecklare", "Lina", "Moraberg", 29, 220, 35, "Gagnef");
		employees[2] = new Employee("E3", "Radiopratare", "Helge", "Skolhage", 58, 200, 20, "Ludvika");
		return employees;
	}


	/*
	 * @Param Scanner för användar input
	 * @Return/få tillbaka ett string värde
	 * Hämta användar input som string och få tillbaka värde
	 * Hämtar input som string och ge tillbaka värde om giltigt
	 */
	public String getString(Scanner keyboard) {
		try {
			String word = keyboard.nextLine(); // Hämta string input från användaren
			return word; // Få tillbaka input
		}
		catch(InputMismatchException e) {
			System.out.println("Invalid input"); // Ogiltig String - error
			return getString(keyboard);
		}

	}

	/*
	 * @Param Scanner för användar input
	 * @Return/få tillbaka double värde
	 * Hämtar input för double och ge tillbaka värde om giltigt
	 */
	public double getDouble(Scanner keyboard) {
		try {
			double num = keyboard.nextDouble();// Hämta double från användaren
			if(num < 0) {
				System.out.println("Cant be a negative number");
				keyboard.nextLine();
				return getDouble(keyboard);
			}
			return num; // Return/få tillbaka Double värde från användaren
		}

		catch(InputMismatchException e) {
			System.out.println("Please enter a valid number");// Ogiltigt double värde - error
			keyboard.nextLine();
			return getDouble(keyboard);
		}
	}
	
	/*
	 * @Param Scanner för användar input
	 * @Return/få tillbaka int värde
	 * Hämtar input för int och ge tillbaka värde om giltigt
	 */
	public int getInt(Scanner keyboard) {
		try {
			int num = keyboard.nextInt();// Hämta int från användaren
			if(num < 0) {
				System.out.println("Cant be a negative number");
				keyboard.nextLine();
				return getInt(keyboard);
			}
			return num; // Return/få tillbaka int från användaren
		}

		catch(InputMismatchException e) {
			System.out.println("Non valid number");// Ogiltigt int värde - error
			keyboard.nextLine();
			return getInt(keyboard);
		}
	}

}
