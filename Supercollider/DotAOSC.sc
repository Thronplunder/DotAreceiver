DotaOSC {
	var <>players, <>radiantKills, <>direKills, <>backgroundsynth, <>unitsAlive, <>goldDif, <>experienceDif, <>goldBus, <>goldSynth, <>xpBus, <>xpSynth;
	*new {
		^super.new()
	}

	//initialise all the variables, boot the server and start playing sounds
	initialise {
		this.goldDif = 0;
		this.radiantKills = 0;
		this.direKills = 0;
		this.players = List.newClear();
		Server.local.waitForBoot({
			this.loadOSCDefs();
			this.loadSynthDefs();
			this.goldBus = Bus.audio(Server.local, 1);
			this.xpBus = Bus.audio(Server.local, 1);
			//goldSynth = Synth(\Gold, [bus: this.goldBus, gold: 0]);
			//xpSynth = Synth(\Exp, [bus: this.xpBus, xp: 0]);
		});
		goldSynth = Synth(\Gold, [bus: this.goldBus, gold: 0]);
		xpSynth = Synth(\Exp, [bus: this.xpBus, xp: 0]);
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

	onPlayerSpawned{|id, networth, position, teamID|
		"Player spawned".postln;
		this.players.add(Player.new(id, networth, [0,0], teamID ));
	}
}
