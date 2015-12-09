+ DotaOSC {
	loadSynthDefs{
		"Load Synths".postln;

		SynthDef(\Background, {|freq, amp, direGold = 1, direXP = 1, radGold = 1, radXP = 1|
			var root = SinOsc.ar( [48, 60, 72, 55, 57, 79].midicps) * 0.5;
			var rad = SinOsc.ar([50, 62, 74].midicps) * 0.15 * radGold.lag(1);
			var dire = SinOsc.ar([53, 65, 77].midicps) * 0.15 * direGold.lag(1);
			var snd = root + dire + rad;
			Out.ar(0, Splay.ar(snd * 0.5));
		}).add;

		SynthDef(\RadiantHero, {|amp = 0.1, detune|
			var scale = (Scale.major.degrees + (4 * 12)).scramble;
			var trig = LFPulse.ar(1.5);
			var demand = Demand.ar(trig, 0, Dseq(scale, inf) + detune);
			var snd = SinOsc.ar(demand.midicps, 0.1);
			snd = snd * EnvGen.ar(Env.perc(0.01, 0.9), trig);
			Out.ar(0, Splay.ar(snd * 0.1));
		}).add;

		SynthDef(\DireHero, {|amp = 0.1|
			var scale = (Scale.harmonicMajor.degrees + (4 * 12)).scramble;
			var trig = LFPulse.ar(1.5);
			var demand = Demand.ar(trig, 0, Dseq(scale, inf));
			var snd = LFSaw.ar(demand.midicps, 0.1);
			snd = snd * EnvGen.ar(Env.perc(0.01, 0.9), trig);
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
		}).add.play;

		SynthDef(\HeroSpawn, {|freq, amp = 0.1, note = 60, mod, dur = 5|
			var note1 = Line.kr(note.midicps, (note + 7).midicps, dur);
			var note2 = Line.kr((note + 7).midicps, (note + 12).midicps, dur);
			var snd = SinOsc.ar(note1 + note2);
			var env = EnvGen.kr(Env.perc(0.01, dur + 0.5), doneAction:2);
			snd = snd * env;
			Out.ar(0, Splay.ar(snd * amp));
		}).add;
	}
}