import java.util.Scanner;

public class Launcher
{
	public static void main(String[] args)
	{
		Assembler a1 = new Assembler();
		String command = null;
		boolean finished = false;
		
		Scanner kb = new Scanner(System.in);
		
		System.out.println("Assembler - Phillip Pham");
		System.out.println("*** Begin entering assembly code: \n");
		
		// Begins the loop to start the user input. Exits when halt is input.
		while(!finished)
		{
			command = kb.nextLine();
			
			if(command.toLowerCase().contains("halt"))
			{
				finished = true;
			}
			else
			{
				a1.parseCommand(command);
			}
		}
		
		// Display the final machine code
		System.out.println(a1.display());
		
		// Exit gracefully
		kb.close();
		System.exit(0);
	}
}