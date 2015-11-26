
//actually spawn an instance of the Game
v = DotaOSC.new();
v.initialise();

OSCdef(\test, { |msg, time, addr, rport|
	msg.postln;
	v.initialise()}, "/Dota2PlayersLoaded" )


[60, 55, 67, 35].midicps


v.players
v.onPlayerSpawned(1, 22, nil, 0)


SynthDef(\Background, {|freq, amp, gold, xp|
	var root = SinOsc.ar( [48, 60, 72, 55, 57, 79].midicps) * 0.5;
	var rad = SinOsc.ar([50, 62, 72].midicps) * 0.15 * Gendy1.ar(1, 2, 1, 1, 1.2, 0.6);
	var dire = SinOsc.ar([53, 65, 77].midicps) * 0.15 * Gendy1.ar(1, 2, 1, 1, 1, 0.5);
	var snd = root + dire + rad;
	Out.ar(0, Splay.ar(snd * 0.025));
}).add;


Synth(\Background);

Synth(\RadiantHero)