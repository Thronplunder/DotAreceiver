+ DotaOSC {
	loadOSCDefs{
		//OSCdef(\Playersloaded, {}, "/Dota2PlayersLoaded");
		OSCdef(\PlayerSpawned, {|msg, time, addr, rport|
			msg.postln;
			this.onPlayerSpawned(msg[2], msg[3], nil, msg[1]);
		}, "/Dota2PlayerSpawned");

		OSCdef(\NPCSpawned, {|msg, time, addr, rport|
			msg.postln;
			}, "/Dota2NPCSpawn");

		OSCdef(\HeroSpawned, {|msg, time, addr, rport|
			msg.postln;
			this.onHeroSpawned(msg[1]);
			}, "/Dota2HeroSpawned");

		OSCdef(\ItemPickedUp, {|msg, time, addr, rport|
			msg.postln;
			}, "/Dota2ItemPickup");

		OSCdef(\ItemPurchased, {|msg, time, addr, rport|
			msg.postln;
			}, "/Dota2ItemPurchased");

		OSCdef(\AbilityUsed, {|msg, time, addr, rport|
			msg.postln;
			}, "/Dota2AbilityUsed");

		OSCdef(\LevelUp, {|msg, time, addr, rport|
			msg.postln;
			this.onLevelUp(msg[1]);
			}, "/Dota2LevelUp");

		OSCdef(\LastHit, {|msg, time, addr, rport|
			msg.postln;
			this.onLastHit(msg[2], msg[1]);
			}, "/Dota2LastHit");

		OSCdef(\TreeCut, {|msg, time, addr, rport|
			msg.postln;
			this.onTreeCut(msg[1], msg[2]);
			}, "Dota2TreeCut");

		OSCdef(\HeroKill, {|msg, time, addr, rport|
			msg.postln;
			this.onHeroKill(msg[1]);
			}, "/Dota2HeroKilled");

		OSCdef(\Networth, {|msg, time, addr, rport|
			msg.postln;
			this.onNetworthChange(msg[1], msg[2]);
			}, "/Dota2Networth");

		OSCdef(\XP, {|msg, time, addr, rport|
			msg.postln;
			this.onXPChange(msg[2], msg[1]);
			}, "/Dota2XP");
		"OSCDefs load".postln;
	}
}