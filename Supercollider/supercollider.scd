//test functions, that receive a message for last hits and spawned npcs and print out the reveived values
OSCdef(\test, { |m, t, a, r| m.postln }, "/LastHit" )

