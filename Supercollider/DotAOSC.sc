DotaOSC {
	var <>players, <>radiantKills, <>direKills, <>backgroundsynth, <>unitsAlive, <>goldDiff, <>experienceDiff, <>direGoldBus, <>direXPBus, <>radGoldBus, <>radXPBus, <>maxXPDiff, <>maxGoldDiff;
	*new {
		^super.new()
	}

	//initialise all the variables, boot the server and start playing sounds
	initialise {
		this.goldDiff = 0;
		this.radiantKills = 0;
		this.direKills = 0;
		this.players = Dictionary.new(10);
		maxXPDiff = 0;
		maxGoldDiff = 0;
		Server.local.waitForBoot({
			this.loadOSCDefs();
			this.loadSynthDefs();
			//this.direGoldBus = Bus.control(Server.local, 1);
			//this.direXPBus = Bus.control(Server.local, 1);
			//this.radGoldBus = Bus.control(Server.local);
			//this.radXPBus = Bus.control(Server.local);
			//this.maxGoldDiff = 1;
			//this.direGoldBus.set(1);
			//this.direXPBus.set(1);
			//this.radGoldBus.set(1);
			//this.radXPBus.set(1);
		});
		this.backgroundsynth = Synth(\Background,[\direGold, 1,
			\radGold, 1,
			\direXP, 1,
			\radXP, 1
		]);
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
		this.experienceDiff = radXP - direXP;
		if(this.maxXPDiff < this.experienceDif.abs, {this.maxXPDiff = this.experienceDiff.abs;});
		this.backgroundSynth.set(\direXP, direXP / this.maxXPDiff, \radXP, radXP / this.maxXPDiff);
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
		this.goldDiff = radNet - direNet;
		if(this.maxGoldDiff < this.golsDif.abs, {this.maxGoldDiff = this.goldDiff.abs;});
		this.backgroundSynth.set(\direGold, direNet / this.maxGoldDiff, \radGold, radNet / this.maxGoldDiff);
	}

	networthChange {|playerID, gold|
		this.players.at(playerID).changeNetworth(gold);
		this.calcNetworthDiff();
	}

	xpChange {|playerID, xp|
		this.players.at(playerID).changeXP(xp);
		this.calcXPDiff();
	}

	onPlayerSpawned{|id, networth, position, teamID|
		"Player spawned".postln;
		this.players.add(id -> Player.new(id, networth, [0,0], teamID ));
	}

	onHeroSpawned{|playerID|
		this.players.at(playerID).spawn(true);
	}

	onLevelUp{|playerID|
		this.players.at(playerID).incLevel();
	}

	onLastHit{|playerID, isHeroKill|
		if(isHeroKill != 0, {
			players.at(playerID).incKills();
		}, {
				Synth(\LastHit, [\note, Scale.mixolydian.degrees.choose + 60]);
		});
	}

	onHeroKill{|playerID|
		var player = this.players.at(playerID);
		player.incDeaths();
	}

	onTreeCut{|xCoord, yCoord|

	}

	onNetworthChange{|playerID, newNetworth|
		this.players.at(playerID).changeNetworth(newNetworth);
	}

	onXPChange{|playerID, newXP|
		this.players.at(playerID).changeXP(newXP);
	}
}
