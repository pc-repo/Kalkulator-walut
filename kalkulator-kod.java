package PakietCalc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class InterfejsTest extends JFrame implements ActionListener
{

	private JButton bPolicz, bWyjscie, bSynchro, bSynchro2;
	private JLabel lZegar, lZegar2, lZegar3, l1, l2, l3, l4, l5, l6, l7;   
	private JTextField tKasa, tKasa1, tKasa2, tKasa3, tKasa4, tKasa5;
	private JMenuBar menuBar;
	private JMenu mPlik, mOpcje, mPomoc;
	private JMenuItem miWczytaj, miZapisz, miZamknij, miTematy, miOProgramie;
	private JComboBox cbWaluta;
	private ButtonGroup bgGrupa;
	private JRadioButton rbPln, rbObca;
	
	private static ArrayList<String> nazwa = new ArrayList<String>();
	private static ArrayList<Integer> przelicznik = new ArrayList<Integer>();
	private static ArrayList<String> kod = new ArrayList<String>();
	private static ArrayList<Double> kurs = new ArrayList<Double>();
	//String tabela, data;
	static int wierszy = 0;
	
	static double[] stan = new double[3] ;
	static double[] warto = new double[3];
	static boolean way = true;
	static int possit = -1;
	
	public static Document doks;
	public static String strona = "http://www.nbp.pl/Kursy/xml/lastA.xml";
	public static String plikNazwa = "lastA.xml", info;
	public static boolean see = false;
	public static boolean on = true;
	public static boolean connect = true;
	public static boolean test = false;
	public static File plikk = new File("plikNazwa");
	
	public static String teraz2;

	SimpleDateFormat simpleDateHere = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");	
	String teraz = "2014-10-23 12:00:00";
	Timer t = new Timer(1000, this);
	Timer tt = new Timer(3000, this);
	
	public void lista()
	{
		for(String s:nazwa)
			cbWaluta.addItem(s);
			add(cbWaluta);
			System.out.println(wierszy + " " + nazwa.size());
			
			lZegar.setText("Twój wybór:     Wybierz z listy");
			lZegar2.setText("Data kursu:      " + teraz2);
			tt.start();
	}
	
	public void zerowanie()
	{
		cbWaluta.removeAllItems();
		cbWaluta.addItem("Lista");
			
		if(nazwa.size() > 0)
		{
			nazwa.clear();
			przelicznik.clear();
			kod.clear();
			kurs.clear();
		}
		
		tKasa3.setText("0.0");
		tKasa4.setText("0.0");
		tKasa5.setText("0.0");

		possit =-1;
	}
	
	public void stan()
	{
		try
		{
			stan[0] = Double.parseDouble(tKasa.getText());
			stan[1] = Double.parseDouble(tKasa1.getText());
			stan[2] = Double.parseDouble(tKasa2.getText());
		}
		catch (NumberFormatException e) 
		{
			tKasa4.setText("Podaj argumenty liczbowe");
		}
	}
	
	private void policz(boolean way)
	{
		if(possit >= 0 ) 
		{	
			warto[1] = stan[1] * kurs.get(possit) * przelicznik.get(possit);
			warto[0] = warto[1] - warto[1] * stan[0]/100;
			warto[2] = warto[1] + warto[1] * stan[0]/100;
			if(way==false)
			{
				warto[1] = stan[1] * 1 /(kurs.get(possit) * przelicznik.get(possit));
				warto[0] = warto[1] + warto[1] * stan[0]/100;
				warto[2] = warto[1] - warto[1] * stan[0]/100;
			}
		}
		warto[0] = round(warto[0]);
		warto[1] = round(warto[1]);
		warto[2] = round(warto[2]);
			
	}
	
	private void wyswietl()
	{
		if(possit >= 0 ) 
		{
			tKasa3.setText(String.valueOf(warto[0]));
			tKasa4.setText(String.valueOf(warto[1]));
			tKasa5.setText(String.valueOf(warto[2]));
		}
	}
	
	static public double round(double d) 
	{
		//NumberFormat nf = NumberFormat.getInstance();
		//nf.setMaximumFractionDigits(ic);
		//nf.setMinimumFractionDigits(ic);
		
		DecimalFormat nf = new DecimalFormat("###.####");
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(0);
		
		return Double.parseDouble((nf.format(d)).replaceAll(",", ".").replaceAll(" ", "") );
	}
		
	public InterfejsTest()
	{
		
		setSize(600, 570);
		setTitle("DibsCalc-BefrEe 1.0");
		setLayout(null);
		t.start();
				
		//etykiety
		 
		lZegar = new JLabel("Twój wybór:     Sychronizuj/Wczytaj");
		lZegar.setBounds(40, 360, 200, 20);
		add(lZegar);
		
		lZegar2 = new JLabel("Data kursu:      " + teraz);
		lZegar2.setBounds(40, 400, 300, 20);
		add(lZegar2);
		
			
		lZegar3 = new JLabel("Data obecna:   " + teraz);
		lZegar3.setBounds(40, 440, 300, 20);
		add(lZegar3);
		
		l1 = new JLabel("Mar¿a sprzeda¿ [%]");
		l1.setBounds(37, 30, 150, 20);
		add(l1);
		
		l2 = new JLabel("PODAJ KWOTÊ");
		l2.setBounds(43, 140, 100, 20);
		add(l2);
		
		l3 = new JLabel("Mar¿a kupno [%]");
		l3.setBounds(43, 250, 100, 20);
		add(l3);
		
		l4 = new JLabel("Wartoœæ - sprzeda¿");
		l4.setBounds(189, 30, 150, 20);
		add(l4);
		
		l5 = new JLabel("W PRZELICZENIU:");
		l5.setBounds(195, 140, 150, 20);
		add(l5);
		
		l6 = new JLabel("Wartoœæ - kupno");
		l6.setBounds(195, 250, 100, 20);
		add(l6);
		
		l7 = new JLabel("");
		l7.setBounds(380, 140, 150, 20);
		add(l7);
		
		//pola tekstowe
		
		tKasa = new JTextField("1");
		tKasa.setBounds(40,70,100,30);
		add(tKasa);
		tKasa.setToolTipText("Wartoœæ procentowa mar¿y naliczanej przy zakupie waluty. Koszt wymiany waluty.");
		
		tKasa1 = new JTextField("1");
		tKasa1.setBounds(40,175,100,40);
		add(tKasa1);
		tKasa1.setToolTipText("Kwota do wymiany.");
		
		tKasa2 = new JTextField("1");
		tKasa2.setBounds(40,290,100,30);
		add(tKasa2);
		tKasa2.setToolTipText("Wartoœæ procentowa mar¿y odliczana od kwoty kursu. Zysk sprzedaj¹cego. ");
		
		tKasa3 = new JTextField("0");
		tKasa3.setBounds(170,70,150,30);
		add(tKasa3);
		tKasa3.setToolTipText("Wymiana po kursie sprzeda¿y.");
		
		tKasa4 = new JTextField("");
		tKasa4.setBounds(170,180,150,30);
		add(tKasa4);
		tKasa4.setToolTipText("Wymiana po kursie œrednim NBP.");
		
		tKasa5 = new JTextField("0");
		tKasa5.setBounds(170,290,150,30);
		add(tKasa5);
		tKasa5.setToolTipText("Wymiana po kursie zakupu.");
		
		
		//batony
		
		bWyjscie = new JButton("EXIT");
		bWyjscie.setBounds(370,420,150,35);
		add(bWyjscie);
		bWyjscie.addActionListener(this);
		
		bPolicz = new JButton("POLICZ");
		bPolicz.setBounds(370,178,150,35);
		add(bPolicz);
		bPolicz.addActionListener(this);
		
		bSynchro = new JButton("SYNCHRONIZUJ");
		bSynchro.setBounds(370,40,150,30);
		add(bSynchro);
		bSynchro.addActionListener(this);
		
		bSynchro2 = new JButton("Raport");
		bSynchro2.setBounds(370,90,150,25);
		add(bSynchro2);
		bSynchro2.addActionListener(this);
		
		bgGrupa = new ButtonGroup();
		bgGrupa.add(rbPln);
		bgGrupa.add(rbObca);
				
		rbPln = new JRadioButton("Na PLN");
		rbPln.setBounds(380, 225, 100, 20);
		bgGrupa.add(rbPln);
		add(rbPln);
		rbPln.addActionListener(this);
				
		rbObca = new JRadioButton("Na obc¹", true);
		rbObca.setBounds(380, 250, 100, 20);
		bgGrupa.add(rbObca);
		add(rbObca);
		rbObca.addActionListener(this);
		
		
		
		//menu

		menuBar = new JMenuBar();
		
		mPlik = new JMenu("Plik"); 
		miWczytaj = new JMenuItem("Wczytaj plik");
		miZapisz = new JMenuItem("Zapisz plik");
		miZamknij = new JMenuItem("Zamknij program");
		miZamknij.addActionListener(this);
		
		mPlik.add(miWczytaj);
		miWczytaj.addActionListener(this);
		mPlik.add(miZapisz);
		miZapisz.addActionListener(this);
		mPlik.addSeparator();
		mPlik.add(miZamknij);
	
		
		mOpcje = new JMenu("Opcje");
		
		mPomoc = new JMenu("Pomoc");
		//miTematy = new JMenuItem("Tematy pomocy");
		//mPomoc.add(miTematy);
		miOProgramie = new JMenuItem("O programie");
		miOProgramie.addActionListener(this);
		mPomoc.add(miOProgramie);
			
		
		setJMenuBar(menuBar);
		menuBar.add(mPlik);
		menuBar.add(mOpcje);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(mPomoc);
		
		cbWaluta = new JComboBox();
		cbWaluta.setBounds(370, 290, 150, 30);
		
		cbWaluta.addItem("Lista walut");
		add(cbWaluta);
		cbWaluta.addActionListener(this);
		
	}
	
	public static void main(String[] args) throws IOException
	{
		InterfejsTest frontlook = new InterfejsTest();
		frontlook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frontlook.setVisible(true);
				
	}
	
	@Override
	public void actionPerformed(ActionEvent eve)
	{
		Object what = eve.getSource();
	
		JFileChooser fc = new JFileChooser();
		
		if (what == miWczytaj)
		{	
			zerowanie();
			
			if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
				{
					File plik = fc.getSelectedFile();
					try 
					{
						PrintWriter pw = new PrintWriter(plikk);
						//System.out.println(plikk);
						Scanner sc = new Scanner(plik);
						while (sc.hasNextLine())
						{
							pw.println(sc.nextLine());
							wierszy++;
						}						
						sc.close();
						pw.close();			
					} 
					catch (FileNotFoundException e) {e.printStackTrace();}
				
					try 
					{
						wczytaj();
					} 
					catch (IOException e) 
					{
					e.printStackTrace();
					}
					lista();
					l7.setText("         Plik odczytany ");					
				}
		}
		else if (what == miZapisz)
		{
			if(wierszy > 0)
			{
				if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
				{
					File plik = fc.getSelectedFile();
				
					try 
					{
						PrintWriter pw = new PrintWriter(plik);
						Scanner sc = new Scanner(plikk);
						while(sc.hasNext()) 
							pw.println(sc.nextLine()+"\n");
						sc.close();
						pw.close();
					} 
					catch (FileNotFoundException e) 
					{
						e.printStackTrace();
					}
					
					l7.setText("          Plik zapisany ");
					tt.start();
				}
				
				
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Brak danych do utworzenia pliku.\n      Pobierz tabelê kursów.");
			}
			
		}
		else if (what == bWyjscie || what == miZamknij)
		{
			dispose();
		}
		else if (what == cbWaluta)
		{
			possit = cbWaluta.getSelectedIndex()-1;	
			if(possit >= 0 )lZegar.setText("Twój wybór:     " + kod.get(possit) + "    Kurs:  " + kurs.get(possit));
					
		}
		else if (what==t)
		{
			String teraz = simpleDateHere.format(new Date());
			lZegar3.setText("Data obecna:   " + teraz);
		}
		else if (what==tt)
		{
			l7.setText("");
			tt.stop();
		}
		else if (what==bSynchro)
		{
			zerowanie();
			
			try 
			{
				pozyskaj();
			} 
			catch (IOException e) 
			{
				//e.printStackTrace();
			}
			lista();
			l7.setText("    Zsynchronizowano");
		}
		else if (what == bSynchro2)
		{			
			test(doks);
			raport(true);
		}
		else if (what==bPolicz)
		{
			stan();
			policz(way);
			wyswietl();
			
			
			
		}
		else if (what==rbPln) way = false;
		else if (what==rbObca) way = true;
		else if (what==miOProgramie)
		{
			JOptionPane.showMessageDialog(null, "            Autorem programu jest Pawe³ Cabaj \nProgram napisany na zaliczenie przedmiotu JavaEE \n                        WSEI LUBLIN 2014");
		}
	}
	
	public static void parse(Document document, ArrayList<String> nazwa, ArrayList<Integer> przelicznik, ArrayList<String> kod, ArrayList<Double> kurs)
	{
		NodeList lista = document.getElementsByTagName("pozycja");
		
		//System.out.println(lista.getLength());

		for(int id=0; id<lista.getLength(); id++)
		{
		nazwa.add(document.getElementsByTagName("nazwa_waluty").item(id).getTextContent());
		przelicznik.add(Integer.parseInt(document.getElementsByTagName("przelicznik").item(id).getTextContent()));
		kod.add(document.getElementsByTagName("kod_waluty").item(id).getTextContent());
		kurs.add(Double.parseDouble(document.getElementsByTagName("kurs_sredni").item(id).getTextContent().replaceAll(",", ".")));
		}
		
		teraz2 = document.getElementsByTagName("data_publikacji").item(0).getTextContent() + " 12:00:00";
		System.out.println(teraz2);
	}

	public static void test(Document document)
	{
		if(test==true)
		{
			NodeList lista = document.getElementsByTagName("pozycja");
			for(int id=0; id<lista.getLength(); id++)
			{
				System.out.println(id+1);
				System.out.println(nazwa.get(id));
				System.out.println(przelicznik.get(id));
				System.out.println(kod.get(id));
				System.out.println(kurs.get(id));
				System.out.println();
			}
			
		}
		
		
	}
	
	private static void pozyskaj() throws IOException
	{
		InetAddress local = InetAddress.getLocalHost();
		//System.out.println(local);											
		URL url = new URL(strona);
		URLConnection sesja = url.openConnection();			
		
		try
		{
			sesja.connect();
		} 
		catch (Exception e1) 
		{
			connect = false;
			System.out.println("Nie mo¿na nawi¹zaæ sesji.");
		}
		
							
		info = sesja.getPermission().toString();
		
		InputStream stromien = sesja.getInputStream();
			
		Scanner in = new Scanner(stromien);
		PrintWriter zapis = new PrintWriter(plikk);
		
		while (in.hasNextLine())
		{
			String a = in.nextLine();
			if(see == true)System.out.println(a);
			zapis.println(a);
			wierszy++;
		}
								
		zapis.close();
		in.close();
		
					
		try 
		{
			DocumentBuilderFactory fabryka = DocumentBuilderFactory.newInstance();
			DocumentBuilder budynek = fabryka.newDocumentBuilder();
			doks = budynek.parse(plikk);
			doks.getDocumentElement().normalize();
			
			parse(doks, nazwa, przelicznik, kod, kurs);
							
		}
			
		catch (ParserConfigurationException | SAXException e) 
		{
			e.printStackTrace();
		}
		
		test = true;
		
	}
	
	public static void raport(boolean wlacz)
	{
		if(wlacz==true)
		{
			System.out.println("\n"+info);
			System.out.print("\nNazwa pliku: "+ plikNazwa +". Linie: "+ wierszy + ".");
			if(test == true)System.out.println("Dokument pobrany, przygotowany do parsowania.\n");
			if(connect==false) System.out.println("Brak komunikacji. Nie mo¿na nawi¹zaæ po³¹czenia.");
		}
		
	}
	
	public static void wczytaj() throws IOException
	{
		try 
		{
			DocumentBuilderFactory fabryka = DocumentBuilderFactory.newInstance();
			DocumentBuilder budynek = fabryka.newDocumentBuilder();
			doks = budynek.parse(plikk);
			doks.getDocumentElement().normalize();
			
			parse(doks, nazwa, przelicznik, kod, kurs);
				
		}
		
			
		catch (ParserConfigurationException | SAXException e1) 
		{
			e1.printStackTrace();
		}
		
		test = true;
		
	}
	
}
