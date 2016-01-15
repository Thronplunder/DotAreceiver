Player {
	var <>sound, <>networth, <>experience, <>kills, <>deaths, <>position, <>id, <>teamID, <>level, <>alive, <>detune, <>decodeBus;


	*new{|id, networth, position, teamID, bus|
		^super.new.init(id, networth, position,teamID,  bus);
	}
	init{|id, networth, position, teamID, bus|
		position = position ? [0, 0];
		this.networth = networth;
		this.kills = 0;
		this.deaths = 0;
		this.id = id;
		this.teamID = teamID;
		this.level = 1;
		this.alive = true;
		this.position = Point.new(position[0], position[1]);
		this.detune = 0;
		this.decodeBus = bus;
		if(teamID == 2, {
			TempoClock.default.schedAbs(TempoClock.default.nextTimeOnGrid(0.25),{
				this.sound = Synth(\RadiantHero, [\detune, this.detune, \speed,[1.5, 1].choose, \bus, this.decodeBus ]);
			});
		}, {
				TempoClock.default.schedAbs(TempoClock.default.nextTimeOnGrid(0.25),{
					this.sound = Synth(\DireHero, [\detune, this.detune, \speed, [1.5, 1].choose, \bus, this.decodeBus]);
				});
		});
	}
	changeNetworth{|newNetworth|
		this.networth = newNetworth;
	}
	changeXP{|newXP|
		this.experience = newXP;
	}
	incKills{
		this.kills = this.kills + 1;
		this.calcDetune();
	}
	incDeaths{
		this.deaths = this.deaths + 1;
		this.calcDetune();
		this.die();
	}
	incLevel{
		this.level = this.level + 1;
	}
	updatePosition{|newPosition|
		this.position = newPosition;
		this.sound.set(\azi, atan2(this.position.x, this.position.y) / pi);
	}
	getNetworth{
		^this.networth;
	}
	getXP {
		^this.experience;
	}
	getTeamID {
		^this.teamID;
	}
	setAlive{|status|
		this.alive = status;
	}
	getID{
		^this.id;
	}
	calcDetune{
		var killDeathRatio = this.kills / this.deaths;
		if(killDeathRatio > 1, {
			this.detune = 0;
			this.sound.set(\detune, this.detune);
			}, {this.detune = (1 - killDeathRatio);
				this.sound.set(\detune, this.detune);
		});
	}
	die {
		this.setAlive(false);
		this.sound.run(false);
		Synth(\HeroDied);
	}
	spawn {
		Synth(\HeroSpawn);
		this.sound.run(true);
		this.setAlive(true);
	}
}