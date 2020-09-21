import static java.lang.System.out;

public class Loops 
{
	public static void main(String[] args) 
	{
		//out.println("1.");
		int nums[] = {1,2,4,5,6,7};
		out.println("2.");
		for(int i=0;i<nums.length;i++)
		{
			out.println("nums["+i+"] = "+nums[i]);
		}
		out.println("3.");
		for(int i=0;i<nums.length;i++)
		{
			if(nums[i]%2==0)
				out.println("nums["+i+"] = "+nums[i]);
			
		}

	}

}
