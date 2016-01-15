+ DotaOSC {
	loadSynthDefs{
		"Load Synths".postln;

		SynthDef(\Background, {|freq, amp = 0.07, direGold = 1, direXP = 1, radGold = 1, radXP = 1, lagTime = 1|
			var root = SinOsc.ar( [48, 60, 72, 55, 57, 79].midicps);
			var rad = SinOsc.ar([50, 62, 74].midicps) * radGold.lag(lagTime);
			var dire = SinOsc.ar([53, 65, 77].midicps) * direGold.lag(lagTime);
			var snd = (root + dire + rad) * 0.3;
			Out.ar(0, Splay.ar(snd * amp));
		}).add;

		SynthDef(\RadiantHero, {|amp = 0.07, detune = 0, lagTime = 5, speed = 1.5, azi = 0, decodeBus = 0|
			var scale = (Scale.major.degrees + (4 * 12)).scramble;
			var trig = LFPulse.ar(speed);
			var demand = Demand.ar(trig, 0, Dseq(scale, inf) + detune.lag(lagTime));
			var snd = SinOsc.ar(demand.midicps, 0.1);
			snd = snd * EnvGen.ar(Env.perc(0.01, speed * 0.9), trig);
			Out.ar(decodeBus, PanB2.ar(snd * amp, azi));
		}).add;

		SynthDef(\DireHero, {|amp = 0.01, detune = 0, lagTime = 5, speed = 1.5, azi = 0, decodeBus = 0|
			var scale = (Scale.harmonicMajor.degrees + (4 * 12)).scramble;
			var trig = LFPulse.ar(speed);
				var demand = Demand.ar(trig, 0, Dseq(scale, inf) + detune.lag(lagTime));
			var snd = LFSaw.ar(demand.midicps, 0.05);
			snd = snd * EnvGen.ar(Env.perc(0.01, speed * 0.75), trig);
			Out.ar(decodeBus, PanB2.ar(snd * amp, azi));
		}).add;

		//not used anymore
		/*SynthDef(\LastHit, {|freqq, amp = 0.1, note = 60|
			var freq = note.midicps;
			var snd = SinOsc.ar(freq + SinOsc.ar(1.2 * freq).range(freq / -2, freq / 2));
			var env = EnvGen.ar(Env.perc(0.01, 1), doneAction: 2);
			snd = snd * env;
			Out.ar(0, Splay.ar(snd * amp));
		}).add;*/

		SynthDef(\HeroDied, {|freq = 1000, amp = 0.1, dur = 10, note = 60|
			var snd = LFSaw.ar(note.midicps - Line.kr(0, note.midicps / 3, dur ));
			var env = EnvGen.kr(Env.perc(0.01, dur), doneAction: 2);
			snd = LPF.ar(snd, note.midicps * 5);
			snd = snd * env;
			snd = FreeVerb.ar(snd, 0.5, 0.6);
			Out.ar(0, Splay.ar(snd * amp));
		}).add;

		SynthDef(\HeroSpawn, {|freq, amp = 0.1, note = 60, mod, dur = 7|
			var note1 = Line.kr(note.midicps, (note + 7).midicps, dur);
			var note2 = Line.kr((note + 7).midicps, (note + 12).midicps, dur);
			var snd = SinOsc.ar(note1 + note2);
			var env = EnvGen.kr(Env.perc(0.01, dur + 0.5), doneAction:2);
			snd = snd * env;
			Out.ar(0, Splay.ar(snd * amp));
		}).add;

		SynthDef(\lastHits, {|pulseTrig = 0, amp = 0.2|
			var reset = PulseCount.kr(Impulse.kr(50), pulseTrig);
			var notes = Scale.major.degrees + 36;
			var stepper = Stepper.kr(pulseTrig, (reset > 400) , 36, 48, 1, 36).midicps;
			var snd = Saw.ar(stepper) + LFTri.ar(stepper);
			snd = LPF.ar(snd, stepper * 4);
			Out.ar(0, Splay.ar(snd * amp));
		}).add;

		SynthDef(\decodeSynth, {|inBus|
			var w, x, y;
			#w, x, y = In.ar(inBus, 3);
			Out.ar(0, DecodeB2.ar(2, w, x, y));
		}).add;
	}
}