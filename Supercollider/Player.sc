Player {
	var <>sound, <>networth, <>experience, <>kills, <>deaths, <>position, <>id, <>teamID, <>level, <>alive;
	*new{|id, heroID, networth, position, teamID|
		^super.new.init(id, heroID, networth, position);
	}
	init{|id, networth, position, teamID|
		position = position ? [0, 0];
		this.networth = networth;
		this.kills = 0;
		this.id = id;
		this.teamID = teamID;
		this.level = 1;
		this.alive = true;
		this.position = Point.new(position[0], position[1]);
		if(teamID == 0, {
			this.sound = Synth(\RadiantHero);
		}, {
				this.sound = Synth(\DireHero);
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
	}
	incDeaths{
		this.deaths = this.deaths + 1;
	}
	incLevel{
		this.level = this.level + 1;
	}
	updatePosition{|newPosition|
		this.position.x = newPosition[0];
		this.position.y = newPosition[1];
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
}