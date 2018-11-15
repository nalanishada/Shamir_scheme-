import java.util.*;
import java.math.*;

class SSSS {
	BigInteger prime= new BigInteger("100000000003");
	BigInteger secret;
	BigInteger a[];
	BigInteger x[];
	BigInteger y[];
	BigInteger	r_x[];
	BigInteger	r_y[];
	int n;
	int k;
	Scanner s;
	SSSS() {
		s=new Scanner(System.in);
		System.out.print("Enter no of participants: ");
		n=s.nextInt();
		a = new BigInteger[n];
		x = new BigInteger[n];
		y = new BigInteger[n];
		System.out.print("\n\tEnter 1 for treshold shamir secret(n,t)\n	2 for normal secret sharing(n,n) :: ");
		int i=s.nextInt();
		if(i==1) {
			System.out.print("Enter treshold value: ");
			k=s.nextInt();
		}
		else {	k=n;
		}
		r_x = new BigInteger[k];
		r_y = new BigInteger[k];
	}
	
	void create_secret() {
		System.out.print("Enter your secret: ");
		a[0]=new BigInteger(s.nextInt()+"");
		Random r=new Random();
		int cons=10;
		int sub=5;
		int i;
		for(int j=1;j<k;j++) {
			i=r.nextInt(10);
			a[j]=new BigInteger((i+cons)+"");
			cons+=cons;
		}
		for(int j=0;j<n;j++) {
			i=r.nextInt(10);
			x[j]=new BigInteger((i+sub)+"");
			sub+=sub;
		}
	}
	
	void create_shares() {
		
		for(int i=0;i<n;i++) {
			y[i]=new BigInteger(0+"");
			for(int j=0;j<k;j++) {
				BigInteger aa;
				aa=(x[i].pow(j)).multiply(a[j]);
				y[i]=y[i].add(aa);
			}
			y[i]=y[i].remainder(prime);
		}
		
		System.out.println("\n\t\t\tSHARES::\n");
		for(int i=0;i<n;i++) {
			System.out.println("\t"+(i+1)+"\t"+x[i].longValue()+ " <--> " +y[i].longValue()+"\n");
		}
		System.out.println();
	}
	
	int legranch() {
		BigInteger scrt = new BigInteger("0");
		for(int i=0;i<k;i++) {
			BigInteger temp = new BigInteger("1");
			for(int j=0;j<k;j++) {
				if(i==j) continue;
				BigInteger cal=x[j].subtract(x[i]);
				cal=cal.modInverse(prime);
				cal=cal.multiply(x[j]);
				temp=temp.multiply(cal);
				
			}
			scrt=(scrt.add((y[i].multiply(temp)).mod(prime))).mod(prime);
			
			
		}
		
		return (int)scrt.longValue();
	}
	
	
	void reconstruction() {
		System.out.println("\t Enter "+k+" share numbers ::");
		for(int i=0;i<k;i++) {
			int j=s.nextInt();
			r_x[i]=x[j-1];
			r_y[i]=x[j-1];
		}
		
		System.out.println("Secret :: "legranch());
	}
}

class shamir {
	
	public static void main(String [] args) {
			SSSS re= new SSSS();
			re.create_secret();
			re.create_shares();
			re.reconstruction();
	}
}
