package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;

import mrbool.vlc.example.VideoPlayer;

public class GameListener implements ActionListener{
	
	private UltimateFighter game;
	private boolean versus;
	private GameProtocol protocol;
	private IA artificial_intelligence;
	private boolean mute;
	private Attack [] user_attacks;
	private Attack [] rival_attacks;
	private int attack_user_number;
	private JProgressBar user_stamina;
	private JProgressBar rival_stamina;
	private JTextField txtMessage;
	private Timer timer_turno;
	private Timer timer_final;
	private JProgressBar user_life;
	private JProgressBar rival_life;
	private JButton attack_button_1;
	private JButton attack_button_2;
	private JButton attack_button_3;
	private JButton attack_button_4;
	private JButton btnSkip;
	private VideoPlayer player;
	
	public GameListener(UltimateFighter game, boolean versus, GameProtocol protocol, IA artificial_intelligence, VideoPlayer player, boolean mute, Attack [] user_attacks, Attack [] rival_attacks, int attack_user_number, JProgressBar user_stamina, JProgressBar rival_stamina, JTextField txtMessage, JProgressBar user_life, JProgressBar rival_life, JButton attack_button_1, JButton attack_button_2, JButton attack_button_3, JButton attack_button_4, JButton btnSkip) {

		this.game = game;
		this.versus = versus;
		this.protocol = protocol;
		this.artificial_intelligence = artificial_intelligence;
		this.player = player;
		this.mute = mute;
		this.user_attacks = user_attacks;
		this.rival_attacks = rival_attacks;
		this.attack_user_number = attack_user_number;
		this.user_stamina = user_stamina;
		this.rival_stamina = rival_stamina;
		this.txtMessage = txtMessage;
		this.user_life = user_life;
		this.rival_life = rival_life;
		this.attack_button_1 = attack_button_1;
		this.attack_button_2 = attack_button_2;
		this.attack_button_3 = attack_button_3;
		this.attack_button_4 = attack_button_4;
		this.btnSkip = btnSkip;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String result = "";
		boolean overstamina = false;
		
		boolean flag = game.getFlag();
			
		if ((flag && user_stamina.getValue() < user_attacks[attack_user_number].getStamina()) || (!flag && rival_stamina.getValue() < rival_attacks[attack_user_number].getStamina()))
		{
			System.out.println("Dentro del if de te has pasado");
			overstamina = true;
		}
		
		else 
		{
			System.out.println("Mute: "+mute);
			// Could be MP4, AVI, MOV, MKV, WMA, MPG, MP3, WAV, etc.
			
			/*URL hsURL = null;
			try {
				 hsURL = new URL("jar:file:UltimateFighter.jar!/videos/Goku-Punch.mp4");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String path = hsURL.toExternalForm();
			//String path = this.getClass().getClassLoader().getResource("/videos/Goku-Punch.mp4").toExternalForm();
			System.out.println("Ruta video: "+path);
			
			txtMessage.setText(path);*/
			
			
			
			/*try {
				URL resource = GameListener.class.getClassLoader().getResource("videos/Goku-Punch.mp4");
				File video = Paths.get(resource.toURI()).toFile();
				path_video = video.getAbsolutePath();
				System.out.println("Ruta video: "+path_video);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			//FROM ABSOLUTE PATH
			/*path_video = "\\C:\\Users\\Alberto\\workspace\\DI_Final\\bin\\videos\\Goku-Punch.mp4";
			player.play(path);*/
			
			/*String media_path = getFile("/videos/Goku-Genkidama.mp4");
			System.out.println("Ruta video: "+media_path);*/
			/*File f = new File("C:\\Users\\Alberto\\Desktop");
			try {
				PrintWriter writer = new PrintWriter(f, "UTF-8");
				writer.println(media_path);
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			/*String media_path = path+user_attacks[attack_number].getVideo();
			System.out.println(media_path);
			
			player.play(media_path);*/
			
			//FROM VIDEO DIRECTORY
			result = game.play(player, attack_user_number, true);
			
			/*if(attack_number==3)
				player.play("src/videos/Seiya-Punch.mp4");
			else if (attack_number==2)
				player.play("src/videos/Goku-Genkidama.mp4");
			else if (attack_number==1)
				player.play("src/videos/Goku-Dragon.mp4");
			else if (attack_number==0)
				player.play("src/videos/Goku-Kamehameha.mp4");*/
			
		}
			
		if (result.equalsIgnoreCase("Victoria del usuario") || result.equalsIgnoreCase("Derrota del usuario")) 
		{
			
			txtMessage.setText(result);

			timer_turno = new Timer(3000, new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					String result = protocol.startProtocol(attack_user_number, false);
					System.out.println("Dentro de Timer listener final");
					
					if(user_life.getValue() == 0)
					{
						txtMessage.setText("Derrota del usuario");
						UltimateFighter.finalDialog();
					}
					
					else if (rival_life.getValue() == 0)
					{
						txtMessage.setText("Victoria del usuario");
						UltimateFighter.finalDialog();
					}
					
					else
						txtMessage.setText(result);
					
					timer_turno.stop();
				}
			});

			timer_turno.start();
		}

		else if (overstamina)
		{
			txtMessage.setText("Resistencia del ataque mayor que la resistencia del usuario");
			System.out.println("Dentro de finish");
			System.out.println("Result: "+result);
		}
			

		else 
		{
			txtMessage.setText(result);

			if (result.equalsIgnoreCase("Victoria del usuario") || result.equalsIgnoreCase("Derrota del usuario")) 
			{
				txtMessage.setText(result);

				timer_final = new Timer(3000, new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						System.out.println("Dentro de timer final");
						UltimateFighter.finalDialog();
						timer_final.stop();
						// System.exit(0);
					}
				});
				timer_final.start();
			}

			else 
			{
				// RIVAL TURN
				if (versus) 
					game.changePlayer();
				else 
				{
					timer_turno = new Timer(3000, new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							String result = "";
							int rival_attack = artificial_intelligence.IASelectAttack();

							System.out.println("Ataque rival: " + rival_attack);

							if (rival_attack == -1)
								result = protocol.startProtocol(rival_attack, true);
							else
								result = game.play(player, rival_attack, false);

							// result =
							// protocol.startProtocol(attack_rival_number,
							// true);

							System.out.println("Dentro de Timer listener normal");

							if (user_life.getValue() == 0) 
							{
								txtMessage.setText("Derrota del usuario");
								UltimateFighter.finalDialog();
							}

							else if (rival_life.getValue() == 0) 
							{
								txtMessage.setText("Victoria del usuario");
								UltimateFighter.finalDialog();
							}

							else
								txtMessage.setText(result);

							timer_turno.stop();
							UltimateFighter.enableAttackButtons(attack_button_1, attack_button_2, attack_button_3,
									attack_button_4, btnSkip);
						}

					});

					timer_turno.start();
				}
			}

			/*progressBar = new JProgressBar();
			
			MyCustomProgressBarDialogGame progressBarObj = new MyCustomProgressBarDialogGame(progressBar);
			progressBarObj.createProgressUI();

			MyActionPerformerGame actionObj = new MyActionPerformerGame(progressBar, progressBarObj, protocol, txtMessage);
			actionObj.execute();*/
			
			
			//enableAttackButtons(attack_button_1, attack_button_2, attack_button_3, attack_button_4);
		}
	}
	
	//TODO: Hacer una clase IA en UltimateFighter y pasarla al Listener. 
	//TODO: Simplificar el play del GameListener. Poner en el mismo método el inicio y final de la música, etc.
	
	/*static public String getFile(String fileName)
	{
	        InputStream stream = GameListener.class.getResourceAsStream(fileName);

	        try
	        {
	            if (stream == null)
	            {
	                throw new Exception("Cannot find file " + fileName);
	            }

	            return IOUtils.toString(stream, Charset.defaultCharset());
	        }
	        catch (Exception e) {
	            e.printStackTrace();

	            //System.exit(1);
	        }

	        return null;
	}*/
}
