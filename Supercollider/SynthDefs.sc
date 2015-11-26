+ DotaOSC {
	loadSynthDefs{
		SynthDef(\Background, {|freq, amp, gold, xp|
			var root = SinOsc.ar( [48, 60, 72, 55, 57, 79].midicps) * 0.5;
			var rad = SinOsc.ar([50, 62, 72].midicps) * 0.25;
			var dire = SinOsc.ar([53, 65, 77].midicps) * 0.25;
			var snd = root + dire + rad;
			Out.ar(0, Splay.ar(snd * 0.5));
		}).add;

		SynthDef(\Gold, {|gold, bus|
			Out.ar(bus, gold);
		}).add;

		SynthDef(\Exp, {|xp, bus|
			Out.ar(bus, xp);
		}).add;

		SynthDef(\RadiantHero, {|amp = 0.1|
			var scale = (Scale.major.degrees + (4 * 12)).scramble;
			var trig = LFPulse.ar(1.5);
			var demand = Demand.ar(trig, 0, Dseq(scale, inf));
			var snd = SinOsc.ar(demand.midicps, 0.1);
			snd = snd * EnvGen.ar(Env.perc(0.01, 0.9), trig);
			Out.ar(0, Splay.ar(snd * 0.1));
		}).add;

		SynthDef(\DireHero, {|amp = 0.1|
			var scale = (Scale.harmonicMinor.degrees + (4 * 12)).scramble;
			var trig = LFPulse.ar(1.5);
			var demand = Demand.ar(trig, 0, Dseq(scale, inf));
			var snd = LFSaw.ar(demand.midicps, 0.1);
			snd = snd * EnvGen.ar(Env.perc(0.01, 0.9), trig);
			Out.ar(0, Splay.ar(snd * 0.1));
		}).add;

		"Load Synths".postln;
	}
}