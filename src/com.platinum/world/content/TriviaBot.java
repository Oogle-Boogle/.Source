package com.platinum.world.content;

import com.platinum.util.Misc;
import com.platinum.world.World;
import com.platinum.world.entity.impl.player.Player;

public class TriviaBot
{
	public static final int TIMER = 1000; //1800
	public static int botTimer = TIMER;

	public static int answerCount;
	public static String firstPlace;
	//public static String secondPlace;
	//public static String thirdPlace;

	//public static List<String> attempts = new ArrayList<>();

	public static void sequence() {

		if (botTimer > 0)
			botTimer--;
		if (botTimer <= 0) {
			botTimer = TIMER;
			didSend = false;
			askQuestion();
		}
	}

	private static final String[][] TRIVIA_DATA =
			{
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is a virus but also a beer?", "corona" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What country has the world's largest population?", "china" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the cube root of 729?", "9" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the spiciest pepper in the world?", "carolina reaper" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@Who is the founder of Amazon?", "jeff bazos" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What skill allows you to make armour and weapons?", "smithing" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What rank is Wisdom?", "owner" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the name of this server?", "platinum" },
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
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@Who is the Head Admin of Platinum?", "toxic" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the command to get to the world boss? ::", "wb" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@WWhat is the Azazel KC required to kill Ravanas?", "3500" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is the Dzanth KC required to kill King Kong?", "1000" },
					{ "<col=6666FF> [ Platinum Trivia ]:</col> @red@What is Luminitous Warrior armour called?", "luminita" },
			};

	public static boolean acceptingQuestion() {
		return !currentQuestion.equals("");
	}
	public static boolean didSend = false;
	public static String currentQuestion;
	private static String currentAnswer;

	public static void attemptAnswer(Player p, String attempt) {

		if (!currentQuestion.equals("") && attempt.replaceAll("_", " ").equalsIgnoreCase(currentAnswer)) {

			if (answerCount == 0) {
				answerCount++;
				p.getPointsHandler().incrementTriviaPoints(10);
				p.getPointsHandler().refreshPanel();
				firstPlace = p.getUsername();
				World.sendFilteredMessage("<col=AD096E>[Trivia] </col>" + firstPlace + "@bla@ answered first and received 10 points!");
				currentQuestion = "";
				didSend = false;
				botTimer = TIMER;
				answerCount = 0;
			}
		} else {
			if (attempt.contains("question") || attempt.contains("repeat")) {
				p.getPacketSender().sendMessage("@bla@" + (currentQuestion));
				return;
			}

			p.getPacketSender().sendMessage("<col=AD096E>[Trivia] </col>@bla@ Sorry! Wrong answer! The current question is:");
			p.getPacketSender().sendMessage("@bla@ " + (currentQuestion));
		}

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
					World.sendFilteredMessage(currentQuestion);
				}
			}
		}
	}

}
