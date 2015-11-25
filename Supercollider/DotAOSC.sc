DotaOSC {
	var players, radiantKills, direKills, backgroundsynth, unitsAlive, goldDif, experienceDif;
	*new {
		^super.new()
	}

	//initialise all the variables, boot the server and start playing sounds
	initialise {
		this. direNetworth = 0;
		this.radiantNetworth = 0;
		this.radiantKills = 0;
		this.direKills = 0;
		this.players = List.newClear();
		Server.local.waitForBoot({
			this.loadOSCDefs();
			this.gold = Bus.new(numChannels: 1, server: Server.local);
			this.experience = Bus.new(numChannels: 1, server: Server.local);
		});
	}

	addPlayer{|id, heroID, networth, position, team|

			players.add(Player.new(id, networth, [0, 0]));
	}

	calcXPDiff{
		var radXP = 0, direXP = 0;
		players.do({|item|
			if(item.getTeamID() == 0,
				{
					radXP = radXP + item.getXP();
				},
				{
					direXP = direXP + item.getXP();
			});
		});
		this.experience = radXP - direXP;
	}

	calcNetworthDiff{
		var radNet = 0, direNet = 0;
		players.do({|item|
			if(item.getTeamID() == 0,
				{
					radNet = radNet + item.getXP();
				},
				{
					direNet = direNet + item.getXP();
			});
		});
		this.experience = radNet - direNet;
	}

	networthChange {|playerID, gold|
		players.do({|player|
			if(playerID == player.getID(),{
				player.changeNetworth(gold);
			});
		});
		this.calcNetworthDiff();
	}

	xpChange {|playerID, xp|
		players.do({|player|
			if(playerID == player.getID(),{
				player.changeXP(XP);
			});
		});
		this.calcCPDiff();
	}
}
}