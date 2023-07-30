
import java.awt.*;
import javax.swing.*;

class DrawerPanel extends JPanel
{
	DrawerPanel() {
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawLine(100,100,100,500);
		g.drawLine(100,300,700,300);

		double x = 0.0;
		double delta = 2*Math.PI/600.0;

		int xOld = 100;
		int yOld = (int)-(200*(Math.sin(0.0))) + 300;

		for(int i = 0; i < 600; i++) {
//			double y = 0.8*Math.sin(3*x);
//			double y = 0.1*Math.sin(20*x);
			double y = 0.8*Math.sin(3*x) + 0.1*Math.sin(20*x);

			int yPrime = (int)(-y*200) + 300;

			int xNew = 100 + i;
			int yNew = yPrime;
//			g.drawOval(100+i,yPrime,1,1);
			g.drawLine(xOld,yOld,xNew,yNew);
			xOld = xNew;
			yOld = yNew;
			x = x + delta;
		}

	}
}
class DrawerFrame extends JFrame
{
	DrawerFrame() {
		setTitle("Sin Graph");
		setSize(1000,600);
		setLocation(200,200);

		DrawerPanel panel = new DrawerPanel();
		setContentPane(panel);
	}
}
class SinDriver 
{
	public static void main(String[] args) 
	{
		JFrame frame = new DrawerFrame();
		frame.setVisible(true);

	}
}

/*
import java.awt.*;
import javax.swing.*;

class DrawerPanel extends JPanel
{
	DrawerPanel() {
	}
	public void paintComponent(Graphics g) {
		double theta = 0.0;
		double delta = 2*Math.PI/100.0;
		int i,j;

		char sc[][] = new char[60][100];

		for(i = 0; i < 60; i++)
			for(j = 0; j < 100; j++)
				sc[i][j] = ' ';

		for(i = 0; i < 60; i++) sc[i][0] = '|';
		for(j = 0; j < 100; j++) sc[30][j] = '-';
		sc[30][0] = '+';

		for(int index = 0; index < 100; index++) {
			double y = Math.sin(theta);
			int yPrime = -(int)(20*y) + 30;

			sc[yPrime][index] = '*';
			theta = theta + delta;
		}

		for(i = 0; i < 60; i++) {
			for(j = 0; j < 100; j++) {
				if (sc[i][j] != ' ')
				{
					g.drawOval(j,i,1,1);
				}
			}
		}
	}
}
class DrawerFrame extends JFrame
{
	DrawerFrame() {
		setTitle("Sin Graph");
		setSize(400,300);
		setLocation(200,200);

		DrawerPanel panel = new DrawerPanel();
		setContentPane(panel);
	}
}
class SinDriver 
{
	public static void main(String[] args) 
	{
		JFrame frame = new DrawerFrame();
		frame.setVisible(true);

	}
}
*/
/*
class SinDriver 
{
	public static void main(String[] args) 
	{
		double theta = 0.0;
		double delta = 2*Math.PI/100.0;
		int i,j;

		char sc[][] = new char[60][100];

		for(i = 0; i < 60; i++)
			for(j = 0; j < 100; j++)
				sc[i][j] = ' ';

		for(i = 0; i < 60; i++) sc[i][0] = '|';
		for(j = 0; j < 100; j++) sc[30][j] = '-';
		sc[30][0] = '+';

//		int index = 0;
//		while(theta < 2*Math.PI) {
		for(int index = 0; index < 100; index++) {
			double y = Math.sin(2*theta);
			int yPrime = -(int)((0.3)*(20*y)) + 30;

			sc[yPrime][index] = '*';
			theta = theta + delta;
		}

		for(i = 0; i < 60; i++) {
			for(j = 0; j < 100; j++) {
				System.out.printf("%c",sc[i][j]);
			}
			System.out.println();
		}

	}
}
*/
/*
class SinDriver 
{
	public static void main(String[] args) 
	{
		double theta = 0.0;
		double delta = 0.1;

		System.out.printf("0.00  0.00: ");
		for(int i = 0; i < 60; i++) {
			System.out.printf("-");
		}
		System.out.println();

		char line[] = new char[60];

		while(theta < 2*Math.PI*10) {
			for(int j = 0; j < 60; j++) line[j] = ' ';
			line[30] = '|';

			theta = theta + delta;

			double y = Math.cos(theta);
//			double y = 0.2*(-theta*theta*theta + 6*theta*theta - 8*theta);
			System.out.printf("%4.2f %5.2f: ",theta,y);

			int yPrime = (int)(20*y) + 30;

			line[yPrime] = '*';

			for(int j = 0; j < 60; j++) System.out.printf("%c",line[j]);

			System.out.println();
		}
	}
}
*/
