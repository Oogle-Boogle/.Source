package com.arlania.world.content;

import com.arlania.util.Misc;
import com.arlania.util.Stopwatch;
import com.arlania.world.World;

/*
 * @author Bas
 * www.Arlania.com
 */

public class Reminders {
	
	
    private static final int TIME = 900000; //10 minutes
	private static Stopwatch timer = new Stopwatch().reset();
	public static String currentMessage;
	
	/*
	 * Random Message Data
	 */
	private static final String[][] MESSAGE_DATA = { 
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Join 'Help' CC For Help/Tips!"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Do ::benefits To Check out donator Rank Benefits!"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a>Donate to help the server grow! ::store"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Use the command ::drop (npcname) for drop tables"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Deals are on @ ::store - Check discord announcments for extra rewards after donating."},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Donate to help the server grow! ::store"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Use the ::help command to ask staff for help"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a>Donate to help the server grow! ::store"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a>Remember to spread the word and invite your friends to play!"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Donate to help the server grow! ::store"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a>Use ::commands to find a list of commands"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a>Donate to help the server grow! ::store"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Toggle your client settings to your preference in the wrench tab!"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Donate to help the server grow! ::store"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a> Use the command ::drop (npcname) for drop tables"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a>Did you know you can get paid to make videos? PM Wisdom or Nike"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a>Donate to help the server grow! ::store"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a>Use the command ::drop (npcname) for drop tables"},
			{"<img=383><col=eaeaea>[<col=60148a>SERVER<col=eaeaea>]<col=60148a>Make sure you ::vote everyday for amazing Rewards!"},
			
		
	};

	/*
	 * Sequence called in world.java
	 * Handles the main method
	 * Grabs random message and announces it
	 */
	public static void sequence() {
		if(timer.elapsed(TIME)) {
			timer.reset();
			{
				
			currentMessage = MESSAGE_DATA[Misc.getRandom(MESSAGE_DATA.length - 1)][0];
			World.sendMessage(currentMessage);
			World.savePlayers();
					
				}
				
			World.savePlayers();
			}
		

          }

}