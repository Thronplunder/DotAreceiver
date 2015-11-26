+ DotaOSC {
	loadOSCDefs{
		//OSCdef(\Playersloaded, {}, "/Dota2PlayersLoaded");
		OSCdef(\PlayerSpawned, {|msg, time, addr, rport|
			msg.postln;
			this.players.add(Player.new(msg[1], msg[3], nil, msg[2] ))
		}, "/Dota2PlayerSpawned");

		OSCdef(\NPCSpawned, {|msg, time, addr, rport|
			msg.postln;
			}, "/Dota2NPCSpawn");

		OSCdef(\HeroSpawned, {|msg, time, addr, rport|
			msg.postln;
			this.players.do({|player|
				if(player.getID() == msg[1], {
					player.setAlive(true);
				});
			});
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
			this.players.do({|player|
				if(player.getID() == msg[1],{
					player.incLevel();
				})
			});
			}, "/Dota2LevelUp");

		OSCdef(\LastHit, {|msg, time, addr, rport|
			msg.postln;
			}, "/Dota2LastHit");

		OSCdef(\TreeCut, {|msg, time, addr, rport|
			msg.postln;
			}, "Dota2TreeCut");

		OSCdef(\HeroKill, {|msg, time, addr, rport|
			msg.postln;
			this.players.do({|player|
				if(player.getID() == msg[1],{
					player.incDeaths();
					if(player.getTeamID() == 0, {
						this.direKills = this.direKills + 1;},{
							this.radiantKills = this.radiantKills + 1;
					})
				});
			});
			}, "/Dota2HeroKilled");

		OSCdef(\Networth, {|msg, time, addr, rport|
			msg.postln;
			this.players.do({|player|
				if(player.getID() == msg[1], {
					player.changeNetworth(msg[2]);
				})
			})
			}, "/Dota2Networth");

		OSCdef(\XP, {|msg, time, addr, rport|
			msg.postln;
			}, "/Dota2XP");
		"OSCDefs load".postln;
	}
}