
//actually spawn an instance of the Game,
v = DotaOSC.new();
v.initialise();:

OSCdef(\test, { |msg, time, addr, rport|
	msg.postln;
	v.initialise()}, "/Dota2PlayersLoaded" )

//stuff for testing

Scale.major


v.players
v.onPlayerSpawned(1, 22, nil, 0)


SynthDef(\Background, {|freq, amp, gold, xp|
	var root = SinOsc.ar( [48, 60, 72, 55, 57, 79].midicps) * 0.5;
	var rad = SinOsc.ar([50, 62, 72].midicps) * 0.5 * 0.8;
	var dire = SinOsc.ar([53, 65, 77].midicps) * 0.5 * 0.2;
	var snd = root + dire + rad;
	Out.ar(0, Splay.ar(snd * 0.1));
}).add;


SynthDef(\LastHit, {|freqq, amp = 0.2, note = 60|
	var freq = note.midicps;
	var snd = SinOsc.ar(freq + SinOsc.ar(1.2 * freq).range(freq / -2, freq / 2));
	var env = EnvGen.ar(Env.perc(0.01, 1), doneAction: 2);
	snd = snd * env;
	Out.ar(0, Splay.ar(snd * amp));
}).add.play;


SynthDef(\HeroDied, {|freq = 1000, amp = 0.1, dur = 5, note = 60|
	var snd = LFSaw.ar(note.midicps - Line.kr(0, note.midicps / 3, dur ));
	var env = EnvGen.kr(Env.perc(0.01, dur), doneAction: 2);
	snd = LPF.ar(snd, note.midicps * 5);
	snd = snd * env;
	snd = FreeVerb.ar(snd, 0.5, 0.6);
	Out.ar(0, Splay.ar(snd * amp));
}).add.play


SynthDef(\HeroSpawn, {|freq, amp = 0.1, note = 60, mod, dur = 5|
	var note1 = Line.kr(note.midicps, (note + 7).midicps, dur);
	var note2 = Line.kr((note + 7).midicps, (note + 12).midicps, dur);
	var snd = SinOsc.ar(note1 + note2);
	var env = EnvGen.kr(Env.perc(0.01, dur + 0.5), doneAction:2);
	snd = snd * env;
	Out.ar(0, Splay.ar(snd * amp));
}).add
Synth(\HeroSpawn);
Synth(\HeroDied);
Synth(\LastHit, [\note, Scale.major.degrees.choose + 60]);
Synth(\Background)

NetAddr.langPort

{SinOsc.ar() * 0.5}.play