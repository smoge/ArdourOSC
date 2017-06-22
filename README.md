# ArdourOSC

Bindings that allow SuperCollider code to control Ardour Audio Workstation

[Ardour website](https://ardour.org/)

Control of all the commands found in the GUI.

```supercollider
a = ArdourOSC();

// Puts transport in play mode
a.play

// Stops a rolling transport
a.stop	      

// Toggles between play and stop
a.toogle	    

// Stop transport and delete/forget last take
a.stop_forget  

 // Move playhead to start of session
a.start    

 // Move playhead to end of session
 a.end	     

a.add_marker
a.next_marker
a.rev_marker

a.cancel_all_solos
a.rec_enable_toggle
a.save_state

// dB is a float indicating the desired gain in dB:
a.master_gain 	

// position is a float between 0 and 1 setting the desired position of the fader
a.master_fader  

// where gain is a float ranging from -193 to 6 representing the desired gain of the track in dB.
a.strip_gain(1, 4.123)

// etc...
```
