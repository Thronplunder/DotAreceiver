DotaOSC {
	var <>players, <>radiantKills, <>direKills, <>backgroundsynth, <>unitsAlive, <>goldDiff,
	<>experienceDiff, <>maxXPDiff, <>maxGoldDiff, <>lastHitSynth, <>mapXSize, <>mapYSize, <>decodeSynth, <>decodeBus;
	*new {
		^super.new()
	}

	//initialise all the variables, boot the server and start playing sounds
	initialise {
		this.goldDiff = 0;
		this.radiantKills = 0;
		this.direKills = 0;
		this.players = Dictionary.new(10);
		this.maxXPDiff = 0;
		this.maxGoldDiff = 0;
		this.mapXSize = 1;
		this.mapYSize = 1;
		Server.local.waitForBoot({
			this.loadOSCDefs();
			this.loadSynthDefs();
		});
		this.backgroundsynth = Synth(\Background,[\direGold, 1,
			\radGold, 1,
			\direXP, 1,
			\radXP, 1
		]);
		this.decodeBus = Bus.audio(Server.local, 3);
		this.lastHitSynth = Synth(\lastHits);
		this.decodeSynth = Synth(\decodeSynth);
	}

	/* unused
	addPlayer{|id, heroID, networth, position, team|

			players.add(Player.new(id, networth, [0, 0], team, this.decodeBus ));
	}*/

	calcXPDiff{
		var radXP = 0, direXP = 0;
		players.do({|item|
			if(item.getTeamID() == 2,
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
        "networth changed".postln;
		players.do({|item|
			if(item.getTeamID() == 2,
				{
                    item.getNetworth().postln;
					radNet = radNet + item.getNetworth();
				},
				{
                    item.getNetworth().postln;
					direNet = direNet + item.getNetworth();
			});
		});
		this.goldDiff = radNet - direNet;
		if(this.maxGoldDiff < this.goldDiff.abs, {this.maxGoldDiff = this.goldDiff.abs;});
        this.backgroundsynth.set(\direGold, direNet / (direNet+radNet) , \radGold, radNet / (radNet + direNet));
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
		this.players.add(id -> Player.new(id,  networth, [0,0], teamID, this.decodeBus ));
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
				TempoClock.default.schedAbs(TempoClock.default.nextTimeOnGrid(0.25),{
				fork({
					this.lastHitSynth.set(\pulseTrig, 1);
					(0.01).wait;
					this.lastHitSynth.set(\pulseTrig, 0);
				});
				});
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
        this.calcNetworthDiff();
	}

	onXPChange{|playerID, newXP|
		this.players.at(playerID).changeXP(newXP);
	}

	onPositionUpdate{|playerID, xPos, yPos|
		this.	players.at(playerID).updatePosition(Point.new(xPos, yPos));
	}

	onPlayersLoaded{|xSize, ySize|
		this.mapXSize = xSize;
		this.mapYSize = ySize;
	}
}
