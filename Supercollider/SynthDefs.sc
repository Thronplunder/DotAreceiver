+ DotaOSC {
	loadSynthDefs{
		SynthDef(\Background, {|freq, amp, gold, xp|
			var root = SinOsc.ar( [48, 60, 72, 55, 57, 79].midicps) * 0.5;
			var rad = SinOsc.ar([50, 62, 72].midicps) * 0.25;
			var dire = SinOsc.ar([53, 65, 77].midicps) * 0.25;
			var snd = root + dire + rad;
			Out.ar(0, Splay.ar(snd * 0.5));
		}).add
	}
}