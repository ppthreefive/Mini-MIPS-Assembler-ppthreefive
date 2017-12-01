import java.util.HashMap;

public class Assembler
{
	private HashMap<String, String> operations = new HashMap<String, String>();
	private HashMap<String, String> registers = new HashMap<String, String>();
	private String machineCode;
	private int totalWords;

	public Assembler()
	{
		this.machineCode = "";
		this.totalWords = 0;
		
		// Operation, FUNCT
		this.operations.put("ADD", "100000");
		this.operations.put("ADDI", "001000");
		this.operations.put("AND", "100100");
		this.operations.put("ANDI", "001100");
		this.operations.put("BEQ", "000100");
		this.operations.put("LW", "100011");
		this.operations.put("SW", "101011");
		this.operations.put("SRL", "000010");
		this.operations.put("SLL", "000000");
		
		// Register, Number
		this.registers.put("$ZERO", "00000");
		this.registers.put("$V0", "00010");
		this.registers.put("$V1", "00011");
		this.registers.put("$A0", "00100");
		this.registers.put("$A1", "00101");
		this.registers.put("$A2", "00110");
		this.registers.put("$A3", "00111");
		this.registers.put("$T0", "01000");
		this.registers.put("$T1", "01001");
		this.registers.put("$T2", "01010");
		this.registers.put("$T3", "01011");
		this.registers.put("$T4", "01100");
		this.registers.put("$T5", "01101");
		this.registers.put("$T6", "01110");
		this.registers.put("$T7", "01111");
		this.registers.put("$T8", "11000");
		this.registers.put("$T9", "11001");
		this.registers.put("$S0", "10000");
		this.registers.put("$S1", "10001");
		this.registers.put("$S2", "10010");
		this.registers.put("$S3", "10011");
		this.registers.put("$S4", "10100");
		this.registers.put("$S5", "10101");
		this.registers.put("$S6", "10110");
		this.registers.put("$S7", "10111");
	}

	public void parseCommand(String command)
	{
		// Initializing temporary variables
		String tempOp = "";
		String tempResultReg = "";
		String tempSourceOne = "";
		String tempSourceTwo = "";
		
		// Splitting the user input into a String array delimited by spaces
		String input[] = command.toUpperCase().split(" ");
		
		// If the first element of the array matches an operation,
		// temp variables are set to the value in the HashMap with the respective key
		if(this.operations.containsKey(input[0]))
		{
			tempOp = this.operations.get(input[0]);
		}
		
		if(this.registers.containsKey(input[1]))
		{
			tempResultReg = this.registers.get(input[1]);
		}
		
		// Needs to check if the operation is LW or SW so it knows to 
		// use a register or an immediate
		if(input[0].contains("LW") || input[0].contains("SW"))
		{	
			tempSourceOne = input[2];
		}
		else
		{
			//if(registers.containsKey(input[2]))
			//{
				tempSourceOne = registers.get(input[2]);
			//}
		}
		
		// This will set the temp variable of the last part of the user input
		// to either a register or an integer represented by a String
		/*if(input[0].contains("SLL") || input[0].contains("SRL"))
		{
			tempSourceTwo = input[3];
		}*/
		if(this.registers.containsKey(input[3]))
		{
			tempSourceTwo = this.registers.get(input[3]);
		}
		else
		{
			tempSourceTwo = input[3];
		}
		
		this.machineCode += convert(tempOp, tempResultReg, tempSourceOne, tempSourceTwo) + "\n";
		this.totalWords++;
	}
	
	private String convert(String operation, String resultReg, String sourceOne, String sourceTwo)
	{
		String output = "***: ";
		
		if(operation == this.operations.get("ADD"))
		{
			String shamt = "00000";
			String opCode = "000000";
			
			output += opCode + sourceOne + sourceTwo + resultReg + shamt + this.operations.get("ADD");
		}
		else if(operation == this.operations.get("ADDI"))
		{
			String opCode = "001000";
			String sixteenBit = Integer.toBinaryString(Integer.parseInt(sourceTwo)); 
			
			if(sixteenBit.length() > 16)
			{
				sixteenBit = sixteenBit.substring(sixteenBit.length() - 16);
			}
			else if(sixteenBit.length() < 16)
			{
				sixteenBit = String.format("%016d",  Integer.valueOf(Integer.toBinaryString(Integer.parseInt(sourceTwo))));
			}
			
			output += opCode + sourceOne + resultReg + sixteenBit;
		}
		else if(operation == this.operations.get("AND"))
		{
			String shamt = "00000";
			String opCode = "000000";
			
			output += opCode + sourceOne + sourceTwo + resultReg + shamt + this.operations.get("AND");
		}
		else if(operation == this.operations.get("ANDI"))
		{
			String opCode = "001100";
			String sixteenBit = Integer.toBinaryString(Integer.parseInt(sourceTwo)); 
			
			if(sixteenBit.length() > 16)
			{
				sixteenBit = sixteenBit.substring(sixteenBit.length() - 16);
			}
			else if(sixteenBit.length() < 16)
			{
				sixteenBit = String.format("%016d",  Integer.valueOf(Integer.toBinaryString(Integer.parseInt(sourceTwo))));
			}

			output += opCode + sourceOne + resultReg + sixteenBit;
		}
		else if(operation == this.operations.get("BEQ"))
		{
			String opCode = "000100";
			String sixteenBit = Integer.toBinaryString(Integer.parseInt(sourceTwo)); 
			
			if(sixteenBit.length() > 16)
			{
				sixteenBit = sixteenBit.substring(sixteenBit.length() - 16);
			}
			else if(sixteenBit.length() < 16)
			{
				sixteenBit = String.format("%016d",  Integer.valueOf(Integer.toBinaryString(Integer.parseInt(sourceTwo))));
			}
			
			output += opCode + sourceOne + resultReg  + sixteenBit;
		}
		else if(operation == this.operations.get("LW"))
		{
			String opCode = "100011";
			
			String sixteenBit = Integer.toBinaryString(Integer.parseInt(sourceOne)); 
			
			if(sixteenBit.length() > 16)
			{
				sixteenBit = sixteenBit.substring(sixteenBit.length() - 16);
			}
			else if(sixteenBit.length() < 16)
			{
				sixteenBit = String.format("%016d",  Integer.valueOf(Integer.toBinaryString(Integer.parseInt(sourceOne))));
			}
			
			output += opCode + sourceTwo + resultReg + sixteenBit;
		}
		else if(operation == this.operations.get("SW"))
		{
			String opCode = "101011";
			String sixteenBit = Integer.toBinaryString(Integer.parseInt(sourceOne)); 
			
			if(sixteenBit.length() > 16)
			{
				sixteenBit = sixteenBit.substring(sixteenBit.length() - 16);
			}
			else if(sixteenBit.length() < 16)
			{
				sixteenBit = String.format("%016d",  Integer.valueOf(Integer.toBinaryString(Integer.parseInt(sourceOne))));
			}
			
			output += opCode + sourceTwo + resultReg + sixteenBit;
		}
		else if(operation == this.operations.get("SRL"))
		{
			String opCode = "000000";
			String shamt = String.format("%05d", Integer.valueOf(Integer.toBinaryString(Integer.parseInt(sourceTwo))));
			
			output += opCode + "00000"+ sourceOne + resultReg + shamt + this.operations.get("SRL");
		}
		else if(operation == this.operations.get("SLL"))
		{
			String opCode = "000000";
			String shamt = String.format("%05d", Integer.valueOf(Integer.toBinaryString(Integer.parseInt(sourceTwo))));
			
			output += opCode + "00000" +sourceOne + resultReg + shamt + this.operations.get("SLL");
		}
		
		return output;
	}
	
	public String display()
	{
		return this.machineCode + "\n\n*** Assembly complete. Program required " + totalWords + " words of memory.";
	}
}