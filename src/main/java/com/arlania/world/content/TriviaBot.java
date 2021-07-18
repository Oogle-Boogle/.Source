package com.arlania.world.content;

import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;
import java.util.ArrayList;
import java.util.List;
import com.arlania.util.Misc;

public class TriviaBot
{
	public static final int TIMER = 1800;
	public static int botTimer = TIMER;
	public static int answerCount;
	public static String currentQuestion;
	private static String currentAnswer;
	private static List<String> winners = new ArrayList<String>(3);
	public static boolean didSend = false;

	private static final String[][] TRIVIA_DATA = 
	{
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is a virus but also a beer?", "corona" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What country has the world's largest population?", "china" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the cube root of 729?", "9" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the spiciest pepper in the world?", "carolina reaper" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@Who is the founder of Amazon?", "jeff bazos" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What skill allows you to make armour and weapons?", "smithing" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the cube root of 729?", "9" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What rank is Wisdom?", "owner" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the name of this server?", "platinum" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@How much is a slayer gem?", "1" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the armour set you get when you start the server?", "starter" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the command when you need assistance in game? ::", "help" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What skill has a magnifying glass?", "scavenger" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What skill has a wolf?", "summoning" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the command to vote? ::", "vote" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What key do you need to open the silver chest at home?", "medium" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the form of currency on Platinum?", "tax bags" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What KC is needed to participate in raids?", "1000" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What key do you need to open the chest with wings?", "raids" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@Who is the discord manager?", "kroxy" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@Who is the co-owner of Platinum?", "nike" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@Who is the Head Admin of Platinum?", "toxic" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the command to get to the world boss? ::", "wb" }, 
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@WWhat is the Azazel KC required to kill Ravanas?", "3500" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@Who is better at warzone RoDaddy or Toxic?", "rodaddy" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the Dzanth KC required to kill King Kong?", "1000" },
			{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is Luminitous Warrior armour called?", "luminita" }, 
    };
	public static void sequence()
	{
		if (--botTimer <= 1) 
		{
			botTimer = TIMER;
			didSend = false;
			askQuestion();
		}
	}
	public static List<String> getWinners() 
	{
		return winners;
	}
	public static void resetForNextQuestion() 
	{
		if (!winners.isEmpty()) {
		    winners.clear();
		}
		answerCount = 0;
	}
	public static void attemptAnswer(Player p, String attempt) 
	{
		if (hasAnswered(p)) 
		{
			p.sendMessage("You have already answered the trivia question!");
			return;
		}
		if (!currentQuestion.equals("") && attempt.replaceAll("_", " ").equalsIgnoreCase(currentAnswer)) 
		{

			if (answerCount == 0) 
			{
				answerCount++;
				p.getPacketSender().sendMessage("You Recieved 10 trivia points for </col>1st Place.");
				p.getPointsHandler().incrementTriviaPoints(10);
				p.getPointsHandler().refreshPanel();
				winners.add(p.getUsername());
				return;
			}
			if (answerCount == 1) 
			{
				answerCount++;
				p.getPacketSender().sendMessage("You Recieved 6 trivia points for </col>2nd Place.");
				p.getPointsHandler().incrementTriviaPoints(6);
				p.getPointsHandler().refreshPanel();
				winners.add(p.getUsername());
				return;
			}
			if (answerCount == 2) 
			{
				p.getPacketSender().sendMessage("You Recieved 4 trivia points for </col>3rd Place.");
				p.getPointsHandler().incrementTriviaPoints(4);
				p.getPointsHandler().refreshPanel();
				winners.add(p.getUsername());
				World.sendMessage("<img=483> <col=6666FF>[ Platinum Trivia ]: @bla@[1st:@red@" + 
				(winners.size() > 0 ? winners.get(0) : "Nobody!") + "</col> (10 pts)</col>] </col>[2nd:@red@" + 
						(winners.size() > 1 ? winners.get(1) : "Nobody!") + "</col> (6 pts)</col>] [3rd:@red@" + 
				(winners.size() > 2 ? winners.get(2) : "Nobody!") + "</col>  (4 pts)</col>]");
				resetForNextQuestion();
				currentQuestion = "";
				didSend = false;
				botTimer = TIMER;
				answerCount = 0;
				return;
			}
		} else 
		{
			if (attempt.contains("question") || attempt.contains("repeat"))
			{
				p.getPacketSender().sendMessage("<col=800000>" + (currentQuestion));
				return;
			}
			p.getPacketSender().sendMessage("<img=483> [ Platinum Trivia ]: Sorry! Wrong answer! " + (currentQuestion));
			return;
		}
	}
	private static boolean hasAnswered(Player p) 
	{
		for (int i = 0; i < winners.size(); i++) 
		{
			if (winners.get(i).equalsIgnoreCase(p.getUsername())) 
			{
				return true;
			}
		}
		return false;
	}
	public static boolean acceptingQuestion() 
	{
		return currentQuestion != null && !currentQuestion.equals("");
	}
	private static void askQuestion()
	{
		for (int i = 0; i < TRIVIA_DATA.length; i++) 
		{
			if (Misc.getRandom(TRIVIA_DATA.length - 1) == i) 
			{
				if (!didSend) 
				{
					didSend = true;
					currentQuestion = TRIVIA_DATA[i][0];
					currentAnswer = TRIVIA_DATA[i][1];
					resetForNextQuestion();
					World.sendMessage(currentQuestion);
				}
			}
		}
	}

}
