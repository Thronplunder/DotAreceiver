Player {
	var <>sound, <>networth, <>experience, <>kills, <>deaths, <>position, <>id, <>heroID, <>teamID;
	*new{|id, heroID, networth, position, teamID|
		^super.new.init(id, heroID, networth, position);
	}
	init{|id, heroID, networth, position, teamID|
		position = position ? [0, 0];
		this.networth = networth;
		this.kills = 0;
		this.id = id;
		this.heroID = heroID;
		this.teamId = teamID;
		this.position = Point.new(position[0], position[1]);
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
}