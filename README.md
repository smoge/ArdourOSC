

# ArdourOSC

SuperCollider bindings for Ardour 

Control of all the commands found in the GUI.


```supercollider
a = Ardour();

a.play 	      // Puts transport in play mode
a.stop	      // Stops a rolling transport
a.toogle	  // Toggles between play and stop
a.stop_forget // Stop transport and delete/forget last take
a.start       // Move playhead to start of session
a.end	      // Move playhead to end of session
a.add_marker 
a.next_marker
a.rev_marker
a.cancel_all_solos
a.rec_enable_toggle // Toggles master record enable 
a.save_state 
a.master_gain 	// dB is a float indicating the desired gain in dB
a.master_fader  // // position is a float between 0 and 1 setting the desired position of the fader
a.strip_gain(1, 4.123) // where gain is a float ranging from -193 to 6 representing the desired gain of the track in dB.

// etc...

```
