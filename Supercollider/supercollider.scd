
//actually spawn an instance of the Game
v = DotaOSC.new();
v.initialise();
OSCdef(\test, { |msg, time, addr, rport|
	msg.postln;
	v.initialise()}, "/Dota2PlayersLoaded" )


[60, 55, 67, 35].midicps


SynthDef(\Background, {|freq, amp, gold, xp|
	var root = SinOsc.ar( [48, 60, 72, 55, 57, 79].midicps) * 0.5;
	var rad = SinOsc.ar([50, 62, 72].midicps) * 0.25;
	var dire = SinOsc.ar([53, 65, 77].midicps) * 0.1;
	var snd = root + dire + rad;
	Out.ar(0, Splay.ar(snd * 0.5));
}).add

c = Synth(\Background);

{SinOsc.ar()}.play

OSCdef(\NPCSpawn, {|msg, time, addr, rport|
			msg.postln;
			}, "/Dota2NPCSpawn");


NetAddr.langPort

