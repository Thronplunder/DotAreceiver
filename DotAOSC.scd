DotaOSC {
	var players, radiantNetworth, direNetworth, radiantKills, direKills, backgroundsynth, unitsAlive;
	*new {
		+super.new
	}

	//initialise all the variables, boot the server and start playing sounds
	initialise {
		this. direNetworth = 0;
		this.radiantNetworth = 0;
		this.radiantKills = 0;
		this.direKills = 0;
		Server.local.waitForBoot();
	}
}