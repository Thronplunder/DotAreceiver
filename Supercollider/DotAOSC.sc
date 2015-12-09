DotaOSC {
	var <>players, <>radiantKills, <>direKills, <>backgroundsynth, <>unitsAlive, <>goldDiff, <>experienceDiff, <>direGoldBus, <>goldSynth, <>direXPBus, <>radGoldBus, <>radXPBus,  <>xpSynth, <>maxXPDiff, <>maxGoldDiff;
	*new {
		^super.new()
	}

	//initialise all the variables, boot the server and start playing sounds
	initialise {
		this.goldDif = 0;
		this.radiantKills = 0;
		this.direKills = 0;
		this.players = Dictionary.new(10);
		maxXPDiff = 0;
		maxGoldDiff = 0;
		Server.local.waitForBoot({
			this.loadOSCDefs();
			this.loadSynthfs();
			this.direGoldBus = Bus.audio(Server.local, 1);
			this.direXPBus = Bus.audio(Server.local, 1);
			this.radGoldBus = Bus.audio(Server.local);
			this.radXPBus = Bus.audio(Server.local);
		});
		this.goldSynth = Synth(\Gold, [bus: this.goldBus, gold: 0]);
		this.xpSynth = Synth(\Exp, [bus: this.xpBus, xp: 0]);
		this.backgroundsynth = Synth(\Background);
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
		this.direXPBus.set(direXP / this.maxXPDiff);
		this.radXPBus.set(radXP / this.maxXPDiff);

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
		this.direGoldBus.set(direNet / this.maxGoldDiff);
		this.radGoldBus.set(radNet / this.maxGoldDiff);
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
		this.players.add(id, Player.new(id, networth, [0,0], teamID ));
	}

	onHeroSpawned{|playerID|
		this.players.at(playerID).setAlive(true);
	}

	onLevelUp{|playerID|
		this.players.at(playerID).incLevel();
	}

	onLastHit{|playerID, isHeroKill|
		if(isHeroKill, {
			players.at(playerID).incKills();
		}, {
				Synth(\LastHit, [\note, Scale.myxolydian.degrees.choose + 60]);
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
