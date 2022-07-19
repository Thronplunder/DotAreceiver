
//actually spawn an instance of the Game,
v = DotaOSC.new();
Server.default.meter
v.initialise(); //evaluate twice to actually start the backgroundsynth (why? :( )
//stuff for testing

Scale.directory


v.players

5.do({|item|
v.onPlayerSpawned(item, 44,  [0,0], 2);
});

5.do({|item|
v.onPlayerSpawned(item + 5, 44,  [0,0], 1);
});
v.onLastHit(1, 0)
v.onHeroKill(1)
v.onHeroSpawned(1);
v.onPositionUpdate(0, -1, 0)
v.onNetworthChange(0, 1000000)



