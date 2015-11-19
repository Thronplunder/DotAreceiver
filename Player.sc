Player {
	var sound, networth, experience, kills, deaths, position, id, heroID;
	*new{|id, heroID, networth, position = [0, 0]|
		^super.new.init(id, heroID, networth, position);
	}
	init{|id, heroID, networth, position|
		this.networth = networth;
		this.kills = 0;
		this.id = id;
		this.heroID = heroID;
		this.position = Point.new(position[0], position[1]);
	}
	changeNetworth{|newNetworth|
		this.networth = newNetworth;
	}
	incKills{
		this.kills = this.kills + 1;
	}
	incDeaths{
		this.Deaths = this.Deaths + 1;
	}
	updatePosition{||newPosition|
		this.position.x = newPosition[0];
		this.position.y = newPosition[1];
	}
}