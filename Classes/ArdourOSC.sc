/*

a = Ardour()
a.play
a.stop

http://manual.ardour.org/using-control-surfaces/controlling-ardour-with-osc/osc-control/
http://manual.ardour.org/using-control-surfaces/controlling-ardour-with-osc/querying-ardour-with-osc/
*/

ArdourOSC {

	var a, <>port=3819;
	//var <>verbosity=0;

	*new { ^super.new.init }

	init { a = NetAddr("127.0.0.1", port) }

	///////////////////////
	// Transport Control //
	///////////////////////

	// Puts transport in play mode
	play { a.sendMsg("/transport_play") }

	// Stops a rolling transport
	stop {  a.sendMsg("/transport_stop" ) }

	// Toggles between play and stop
	toggle {  a.sendMsg("/toggle_roll" ) }

	// Stop transport and delete/forget last take
	stop_forget {  a.sendMsg("/stop_forget"  ) }

	// where s is a float ranging from -8.0f to 8.0f:
	set_transport_speed { arg s;  a.sendMsg("/set_transport_speed".post, s) }

	// Adds 1.5 times to transport speed to maximum +8 times normal speed
	ffwd {  a.sendMsg("/ffwd" ) }

	// Adds -1.5 times to transport speed to maximum -8 times normal speed
	rewind {  a.sendMsg("/rewind" ) }

	// Move playhead to start of session
	start {  a.sendMsg("/goto_start" ) }

	// Move playhead to end of session
	end {  a.sendMsg("/goto_end" ) }

	// Where bars is a float (+/-) of the number of bars to jump
	jump_bars { arg bars=1;  a.sendMsg("/jump_bars bars"  , bars) }

	// Where seconds is a float (+/-) of the number of seconds to jump
	jump_seconds { arg seconds=1;  a.sendMsg("/jump_seconds", seconds) }

	// Toggle metronome click on and off
	toggle_click {  a.sendMsg("/toggle_click") }

	// (adds marker to the current transport position)
	add_marker {  a.sendMsg("/add_marker") }

	// Removes marker at the current transport position (if there is one)
	remove_marker {  a.sendMsg("/remove_marker"  ) }

	// Move playhead to next marker
	next_marker {  a.sendMsg("/next_marker "  ) }

	// Move playhead to previous marker
	prev_marker {  a.sendMsg("prev_marker"  ) }

	// where spos is the target position in samples and roll is a bool/integer
	// defining whether you want transport to be kept rolling or not
	// `/locate spos roll`
	locate { arg spos, roll;  a.sendMsg("/locate"  , spos, roll) }

	// Toggle loop mode on and off
	loop_toggle {  a.sendMsg("/loop_toggle"  ) }

	// start is the beginning of a loop and end is the end
	// of a loop both are integer frame positions.
	loop_location{ arg start, end;  a.sendMsg("/loop_location"  , start, end) }

	midi_panic {  a.sendMsg("/midi_panic"  ) }

	cancel_all_solos {  a.sendMsg("/cancel_all_solos"  ) }

	////////////////////////
	// Recording control  //
	////////////////////////

	toggle_punch_in {  a.sendMsg("/toggle_punch_in"  ) }

	toggle_punch_out {  a.sendMsg("/toggle_punch_out"  ) }

	// Toggles master record enable
	rec_enable_toggle {  a.sendMsg("/rec_enable_toggle"  ) }

	////////////////////////////
	// Transport Information  //
	////////////////////////////

	// Ardour sends /transport_frame current_frame
	transport_frame {  a.sendMsg("/transport_frame"  ) }

	// Ardour sends
	transport_speed { arg speed;  a.sendMsg("/transport_speed"  , speed) }

	// Ardour sends /record_enabled recordenable_status
	record_enabled {  a.sendMsg("/record_enabled"  ) }

	/////////////////////
	// Editing-related //
	/////////////////////

	undo {  a.sendMsg("/undo"  ) }

	redo {  a.sendMsg("/redo"  ) }

	save_state {  a.sendMsg("/save_state"  ) }

	//////////////////////////////////////
	// Master and Monitor strip control //
	//////////////////////////////////////

	// dB is a float indicating the desired gain in dB
	master_gain  { arg dB;   a.sendMsg("/master/gain"  , dB) }

	// position is a float between 0 and 1 setting the desired position of the fader
	master_fader { arg position;  a.sendMsg("/master/fader"  , position ) }

	// dB is a float from -20 to +20 representing the desired trim gain in dB
	master_trimdB { arg dB;  a.sendMsg("/master/trimdB"  , dB ) }

	// position is a float from 0 to 1 representing the desired pan position
	master_pan_stereo_position { arg position;  a.sendMsg("/master/pan_stereo_position"  , position ) }

	// state is an int of o or 1 representing the desired mute state
	master_mute  {arg state;  a.sendMsg("/master/mute "  , state ) }

	// dB is a float indicating the desired gain in dB
	monitor_gain { arg dB;  a.sendMsg("/monitor/gain"  , dB ) }

	// position is a float between 0 and 1 setting the desired position of the fader
	monitor_fader { arg  position;  a.sendMsg("/monitor/fader"  , position ) }

	// state is an int of 0 or 1 where 1 is muted
	monitor_mute { arg state;  a.sendMsg("/monitor/mute"  , state ) }

	// state is an int of 0 or 1 where 1 is dimmed
	monitor_dim { arg  state;  a.sendMsg("/monitor/dim"  , state ) }

	// state is an int of 0 or 1 where 1 is mono mode
	monitor_mono { arg state;  a.sendMsg("/monitor/mono"  , state) }


	///////////////////////////////
	// Track specific operations //
	///////////////////////////////

	// For each of the following, ssid is the Surface Strip ID for the track
	// Effectively, banking is always being used and the SSID is generated on
	// the fly. The SSID is the position of the strip within bank as an int 1 to
	// bank size. There are no gaps as there have been in the past. Depending on
	// the value of strip_types sent to Ardour, Master and Monitor, may be
	// included in the list of SSIDs or not as set in /set_surface.

	// Change bank to the next higher bank.
	bank_up {  a.sendMsg("/bank_up "  ) }

	// Change bank to the next lower bank.
	bank_down {  a.sendMsg("/bank_down"  ) }

	// where mute_st is a bool/int representing the desired mute state of the
	// track
	strip_mute { arg ssid, mute_st;  a.sendMsg("/strip/mute"  , ssid, mute_st ) }

	// where solo_st is a bool/int representing the desired solo state of the
	// track
	strip_solo { arg ssid, solo_st;  a.sendMsg("/strip/solo "  , ssid, solo_st) }

	// where state is a bool/int representing the desired solo isolate state of
	// the track
	strip_solo_iso { arg ssid, solo_st; "Ardour: ".post;
	a.sendMsg("/strip/solo_iso"  , ssid, solo_st ) }

	// where state is a bool/int representing the desired
	// solo safe/lock state of the track
	strip_solo_safe { arg ssid, solo_st;  a.sendMsg("/strip/solo_safe"  , ssid, solo_st ) }

	// where monitor_st is a bool/int where 1 is forced input monitoring.
	strip_monitor_input { arg ssid, monitor_st; "Ardour: ".post;
	a.sendMsg("/strip/monitor_input"  , ssid, monitor_st)
	}

	// where monitor_st is a bool/int where 1 is forced disk monitoring.
	// When input and disk are both off, Auto monitoring is enabled.
	strip_monitor_disk { arg ssid, monitor_st;
		a.sendMsg("/strip/monitor_disk "  , ssid, monitor_st)
	}
	
	// where rec_st is a bool/int representing the desired rec state of the
	// track
	strip_recenable { arg ssid, rec_st;  a.sendMsg("/strip/recenable"  , ssid, rec_st) }

	// where rec_st is a bool/int representing the desired record safe state of
	// the track
	strip_record_safe { arg  ssid, rec_st;  a.sendMsg("/strip/record_safe "  , ssid, rec_st ) }

	// where invert is a bool/int representing the desired polarity of the track
	strip_polarity { arg ssid, invert;  a.sendMsg("/strip/polarity"  ,  ssid, invert) }

	// where gain is a float ranging from -193 to 6 representing the desired
	// gain of the track in dB.
	strip_gain { arg ssid, gain;  a.sendMsg("/strip/gain"  , ssid, gain) }

	// where position is a float ranging from 0 to 1 representing the fader
	// control position.
	strip_fader { arg ssid, position;  a.sendMsg("/strip/fader"  , ssid, position )}

	// where trim_db is a float ranging from -20 to 20 representing the desired
	// trim of the track in dB.
	strip_trimdB { arg ssid, trim_db;   a.sendMsg("/strip/trimdB "  , ssid, trim_db) }

	// where position is a float ranging from 0 to 1 representing the desired pan position of the track
	strip_pan_stereo_position { arg ssid, position; "Ardour: ".post;
	a.sendMsg("/strip/pan_stereo_position"  , ssid, position)
	}

	//where width is a float ranging from 0 to 1 representing the desired pan
	//width of the track

	strip_pan_stereo_width { arg ssid, width; "Ardour: ".post;
	a.sendMsg("/strip/pan_stereo_width"  , ssid, width)
	}

	//where sendid = nth_send, send_gain is a float ranging from -193 to +6
	//representing the desired gain in dB for the send
	strip_send_gain  { arg ssid, sendid, send_gain;  "Ardour: ".post;
	a.sendMsg("/strip/send/gain"  , ssid, sendid, send_gain)
	}

	// where sendid = nth_send, send_gain is a float ranging from 0 to 1
	// representing the desired position for the send as a fader
	strip_send_fader { arg ssid, sendid, send_gain; "Ardour: ".post;
	a.sendMsg("/strip/send/fader"  , ssid, sendid, send_gain)
	}

	strip_send_enable { arg ssid, sendid, state; "Ardour: ".post;
	a.sendMsg("/strip/send/enable"  , ssid, sendid, state)
	}

	////////////////////////////////
	//  Querying Ardour with OSC. //
	////////////////////////////////

	// http://manual.ardour.org/using-control-surfaces/controlling-ardour-with-osc/querying-ardour-with-osc/

	strip_list  {  a.sendMsg("/strip/list "  )}

	strip_sends { arg ssid;  a.sendMsg("/strip/sends"  , ssid) }

	strip_receives { arg ssid;  a.sendMsg("/strip/receives"  , ssid) }

	strip_plugin_list { arg ssid;  a.sendMsg("/strip/plugin/list ", ssid) }

	strip_plugin_descriptor { arg ssid;  a.sendMsg("/strip/plugin/descriptor"  , ssid) }

	// where piid = nth Plugin, will reset all values to the plugin's original values
	strip_plugin_reset { arg ssid, piid;  a.sendMsg("/strip/plugin/reset"  , ssid, piid) }

	// where piid = nth Plugin, will set the plugin's state to active
	strip_plugin_activate { arg ssid, piid;  a.sendMsg("/strip/plugin/activate"  , ssid, piid) }

	//////////////////////////
	// TODO!
	// Incomplete...
	//////////////////////////


	//////////////////////////
	// List of Menu Actions //
	//////////////////////////

	/*Menu actions
	Every single menu item in Ardour's GUI is accessible by control surfaces or scripts.
	The list below shows all available values of action-name as of Ardour 5.8. You can get
	the current list at any time by running Ardour with the -b flag.*/

	// http://manual.ardour.org/appendix/menu-actions-list/


	// A/B Plugins
	processormenu_ab_plugins { a.sendMsg("ProcessorMenu/ab_plugins") }

	// Activate All
	processormenu_activate_all { a.sendMsg("ProcessorMenu/activate_all") }

	// Delete
	processormenu_backspace { a.sendMsg("ProcessorMenu/backspace") }

	// Clear (all)
	processormenu_clear { a.sendMsg("ProcessorMenu/clear") }

	// Clear (post-fader)
	processormenu_clear_post { a.sendMsg("ProcessorMenu/clear_post") }

	// Clear (pre-fader)
	processormenu_clear_pre { a.sendMsg("ProcessorMenu/clear_pre") }

	// Controls
	processormenu_controls { a.sendMsg("ProcessorMenu/controls") }

	// Copy
	processormenu_copy { a.sendMsg("ProcessorMenu/copy") }

	// Cut
	processormenu_cut { a.sendMsg("ProcessorMenu/cut") }

	// Deactivate All
	processormenu_deactivate_all { a.sendMsg("ProcessorMenu/deactivate_all") }

	// Delete
	processormenu_delete { a.sendMsg("ProcessorMenu/delete") }

	// Deselect All
	processormenu_deselectall { a.sendMsg("ProcessorMenu/deselectall") }

	// Edit...
	processormenu_edit { a.sendMsg("ProcessorMenu/edit") }

	// Edit with generic controls...
	processormenu_edit_generic { a.sendMsg("ProcessorMenu/edit-generic") }

	// Pin Connections...
	processormenu_manage_pins { a.sendMsg("ProcessorMenu/manage-pins") }

	// New Aux Send ...
	processormenu_newaux { a.sendMsg("ProcessorMenu/newaux") }

	// New Insert
	processormenu_newinsert { a.sendMsg("ProcessorMenu/newinsert") }

	// New Plugin
	processormenu_newplugin { a.sendMsg("ProcessorMenu/newplugin") }

	// New External Send ...
	processormenu_newsend { a.sendMsg("ProcessorMenu/newsend") }

	// Paste
	processormenu_paste { a.sendMsg("ProcessorMenu/paste") }

	// Rename
	processormenu_rename { a.sendMsg("ProcessorMenu/rename") }

	// Select All
	processormenu_selectall { a.sendMsg("ProcessorMenu/selectall") }

	// Send Options
	processormenu_send_options { a.sendMsg("ProcessorMenu/send_options") }

	// Hide
	common_hide { a.sendMsg("Common/Hide") }

	// MIDI Tracer
	common_newmiditracer { a.sendMsg("Common/NewMIDITracer") }

	// Quit
	common_quit { a.sendMsg("Common/Quit") }

	// Save
	common_save { a.sendMsg("Common/Save") }

	// Maximise Editor Space
	common_togglemaximaleditor { a.sendMsg("Common/ToggleMaximalEditor") }

	// Maximise Mixer Space
	common_togglemaximalmixer { a.sendMsg("Common/ToggleMaximalMixer") }

	// Toggle Mixer List
	common_togglemixerlist { a.sendMsg("Common/ToggleMixerList") }

	// Toggle Monitor Section Visibility
	common_togglemonitorsection { a.sendMsg("Common/ToggleMonitorSection") }

	// Toggle Record Enable Track 1
	common_togglerecordenabletrack1 { a.sendMsg("Common/ToggleRecordEnableTrack1") }

	// Toggle Record Enable Track 10
	common_togglerecordenabletrack10 { a.sendMsg("Common/ToggleRecordEnableTrack10") }

	// Toggle Record Enable Track 11
	common_togglerecordenabletrack11 { a.sendMsg("Common/ToggleRecordEnableTrack11") }

	// Toggle Record Enable Track 12
	common_togglerecordenabletrack12 { a.sendMsg("Common/ToggleRecordEnableTrack12") }

	// Toggle Record Enable Track 13
	common_togglerecordenabletrack13 { a.sendMsg("Common/ToggleRecordEnableTrack13") }

	// Toggle Record Enable Track 14
	common_togglerecordenabletrack14 { a.sendMsg("Common/ToggleRecordEnableTrack14") }

	// Toggle Record Enable Track 15
	common_togglerecordenabletrack15 { a.sendMsg("Common/ToggleRecordEnableTrack15") }

	// Toggle Record Enable Track 16
	common_togglerecordenabletrack16 { a.sendMsg("Common/ToggleRecordEnableTrack16") }

	// Toggle Record Enable Track 17
	common_togglerecordenabletrack17 { a.sendMsg("Common/ToggleRecordEnableTrack17") }

	// Toggle Record Enable Track 18
	common_togglerecordenabletrack18 { a.sendMsg("Common/ToggleRecordEnableTrack18") }

	// Toggle Record Enable Track 19
	common_togglerecordenabletrack19 { a.sendMsg("Common/ToggleRecordEnableTrack19") }

	// Toggle Record Enable Track 2
	common_togglerecordenabletrack2 { a.sendMsg("Common/ToggleRecordEnableTrack2") }

	// Toggle Record Enable Track 20
	common_togglerecordenabletrack20 { a.sendMsg("Common/ToggleRecordEnableTrack20") }

	// Toggle Record Enable Track 21
	common_togglerecordenabletrack21 { a.sendMsg("Common/ToggleRecordEnableTrack21") }

	// Toggle Record Enable Track 22
	common_togglerecordenabletrack22 { a.sendMsg("Common/ToggleRecordEnableTrack22") }

	// Toggle Record Enable Track 23
	common_togglerecordenabletrack23 { a.sendMsg("Common/ToggleRecordEnableTrack23") }

	// Toggle Record Enable Track 24
	common_togglerecordenabletrack24 { a.sendMsg("Common/ToggleRecordEnableTrack24") }

	// Toggle Record Enable Track 25
	common_togglerecordenabletrack25 { a.sendMsg("Common/ToggleRecordEnableTrack25") }

	// Toggle Record Enable Track 26
	common_togglerecordenabletrack26 { a.sendMsg("Common/ToggleRecordEnableTrack26") }

	// Toggle Record Enable Track 27
	common_togglerecordenabletrack27 { a.sendMsg("Common/ToggleRecordEnableTrack27") }

	// Toggle Record Enable Track 28
	common_togglerecordenabletrack28 { a.sendMsg("Common/ToggleRecordEnableTrack28") }

	// Toggle Record Enable Track 29
	common_togglerecordenabletrack29 { a.sendMsg("Common/ToggleRecordEnableTrack29") }

	// Toggle Record Enable Track 3
	common_togglerecordenabletrack3 { a.sendMsg("Common/ToggleRecordEnableTrack3") }

	// Toggle Record Enable Track 30
	common_togglerecordenabletrack30 { a.sendMsg("Common/ToggleRecordEnableTrack30") }

	// Toggle Record Enable Track 31
	common_togglerecordenabletrack31 { a.sendMsg("Common/ToggleRecordEnableTrack31") }

	// Toggle Record Enable Track 32
	common_togglerecordenabletrack32 { a.sendMsg("Common/ToggleRecordEnableTrack32") }

	// Toggle Record Enable Track 4
	common_togglerecordenabletrack4 { a.sendMsg("Common/ToggleRecordEnableTrack4") }

	// Toggle Record Enable Track 5
	common_togglerecordenabletrack5 { a.sendMsg("Common/ToggleRecordEnableTrack5") }

	// Toggle Record Enable Track 6
	common_togglerecordenabletrack6 { a.sendMsg("Common/ToggleRecordEnableTrack6") }

	// Toggle Record Enable Track 7
	common_togglerecordenabletrack7 { a.sendMsg("Common/ToggleRecordEnableTrack7") }

	// Toggle Record Enable Track 8
	common_togglerecordenabletrack8 { a.sendMsg("Common/ToggleRecordEnableTrack8") }

	// Toggle Record Enable Track 9
	common_togglerecordenabletrack9 { a.sendMsg("Common/ToggleRecordEnableTrack9") }

	// Add Mark from Playhead
	common_add_location_from_playhead { a.sendMsg("Common/add-location-from-playhead") }

	// Import
	common_addexistingaudiofiles { a.sendMsg("Common/addExistingAudioFiles") }

	// Finish Range
	common_alt_finish_range { a.sendMsg("Common/alt-finish-range") }

	// Start Range
	common_alt_start_range { a.sendMsg("Common/alt-start-range") }

	// Add Mark from Playhead
	common_alternate_add_location_from_playhead { a.sendMsg("Common/alternate-add-location-from-playhead") }

	// Jump to Previous Mark
	common_alternate_jump_backward_to_mark { a.sendMsg("Common/alternate-jump-backward-to-mark") }

	// Jump to Next Mark
	common_alternate_jump_forward_to_mark { a.sendMsg("Common/alternate-jump-forward-to-mark") }

	// Remove Mark at Playhead
	common_alternate_remove_location_from_playhead { a.sendMsg("Common/alternate-remove-location-from-playhead") }

	// Attach
	common_attach_editor { a.sendMsg("Common/attach-editor") }

	// Attach
	common_attach_mixer { a.sendMsg("Common/attach-mixer") }

	// Attach
	common_attach_preferences { a.sendMsg("Common/attach-preferences") }

	// Change
	common_change_editor_visibility { a.sendMsg("Common/change-editor-visibility") }

	// Change
	common_change_mixer_visibility { a.sendMsg("Common/change-mixer-visibility") }

	// Change
	common_change_preferences_visibility { a.sendMsg("Common/change-preferences-visibility") }

	// Chat
	common_chat { a.sendMsg("Common/chat") }

	// Cheat Sheet
	common_cheat_sheet { a.sendMsg("Common/cheat-sheet") }

	// Detach
	common_detach_editor { a.sendMsg("Common/detach-editor") }

	// Detach
	common_detach_mixer { a.sendMsg("Common/detach-mixer") }

	// Detach
	common_detach_preferences { a.sendMsg("Common/detach-preferences") }

	// Finish Loop Range
	common_finish_loop_range { a.sendMsg("Common/finish-loop-range") }

	// Finish Punch Range
	common_finish_punch_range { a.sendMsg("Common/finish-punch-range") }

	// Finish Range
	common_finish_range { a.sendMsg("Common/finish-range") }

	// Finish Range from Playhead
	common_finish_range_from_playhead { a.sendMsg("Common/finish-range-from-playhead") }

	// User Forums
	common_forums { a.sendMsg("Common/forums") }

	// Hide
	common_hide_editor { a.sendMsg("Common/hide-editor") }

	// Hide
	common_hide_mixer { a.sendMsg("Common/hide-mixer") }

	// Hide
	common_hide_preferences { a.sendMsg("Common/hide-preferences") }

	// How to Report a Bug
	common_howto_report { a.sendMsg("Common/howto-report") }

	// Jump to Previous Mark
	common_jump_backward_to_mark { a.sendMsg("Common/jump-backward-to-mark") }

	// Jump to Next Mark
	common_jump_forward_to_mark { a.sendMsg("Common/jump-forward-to-mark") }

	// Change
	common_key_change_editor_visibility { a.sendMsg("Common/key-change-editor-visibility") }

	// Change
	common_key_change_mixer_visibility { a.sendMsg("Common/key-change-mixer-visibility") }

	// Change
	common_key_change_preferences_visibility { a.sendMsg("Common/key-change-preferences-visibility") }

	// Help|Manual
	common_manual { a.sendMsg("Common/manual") }

	// Preferences
	common_menu_show_preferences { a.sendMsg("Common/menu-show-preferences") }

	// Next Tab
	common_next_tab { a.sendMsg("Common/next-tab") }

	// Nudge Next Earlier
	common_nudge_next_backward { a.sendMsg("Common/nudge-next-backward") }

	// Nudge Next Later
	common_nudge_next_forward { a.sendMsg("Common/nudge-next-forward") }

	// Nudge Playhead Backward
	common_nudge_playhead_backward { a.sendMsg("Common/nudge-playhead-backward") }

	// Nudge Playhead Forward
	common_nudge_playhead_forward { a.sendMsg("Common/nudge-playhead-forward") }

	// Playhead to Previous Grid
	common_playhead_backward_to_grid { a.sendMsg("Common/playhead-backward-to-grid") }

	// Playhead to Next Grid
	common_playhead_forward_to_grid { a.sendMsg("Common/playhead-forward-to-grid") }

	// Previous Tab
	common_previous_tab { a.sendMsg("Common/previous-tab") }

	// Manual|Reference
	common_reference { a.sendMsg("Common/reference") }

	// Remove Mark at Playhead
	common_remove_location_from_playhead { a.sendMsg("Common/remove-location-from-playhead") }

	// Set Session End from Playhead
	common_set_session_end_from_playhead { a.sendMsg("Common/set-session-end-from-playhead") }

	// Set Session Start from Playhead
	common_set_session_start_from_playhead { a.sendMsg("Common/set-session-start-from-playhead") }

	// Show
	common_show_editor { a.sendMsg("Common/show-editor") }

	// Show
	common_show_mixer { a.sendMsg("Common/show-mixer") }

	// Show
	common_show_preferences { a.sendMsg("Common/show-preferences") }

	// Start Loop Range
	common_start_loop_range { a.sendMsg("Common/start-loop-range") }

	// Start Punch Range
	common_start_punch_range { a.sendMsg("Common/start-punch-range") }

	// Start Range
	common_start_range { a.sendMsg("Common/start-range") }

	// Start Range from Playhead
	common_start_range_from_playhead { a.sendMsg("Common/start-range-from-playhead") }

	// Toggle Editor & Mixer
	common_toggle_editor_and_mixer { a.sendMsg("Common/toggle-editor-and-mixer") }

	// Toggle Mark at Playhead
	common_toggle_location_at_playhead { a.sendMsg("Common/toggle-location-at-playhead") }

	// Window|Scripting
	common_toggle_luawindow { a.sendMsg("Common/toggle-luawindow") }

	// Window|Meterbridge
	common_toggle_meterbridge { a.sendMsg("Common/toggle-meterbridge") }

	// Report a Bug
	common_tracker { a.sendMsg("Common/tracker") }

	// Ardour Website
	common_website { a.sendMsg("Common/website") }

	// Ardour Development
	common_website_dev { a.sendMsg("Common/website-dev") }

	// Add Track, Bus or VCA...
	main_addtrackbus { a.sendMsg("Main/AddTrackBus") }

	// Archive...
	main_archive { a.sendMsg("Main/Archive") }

	// Reset Peak Files
	main_cleanuppeakfiles { a.sendMsg("Main/CleanupPeakFiles") }

	// Clean-up Unused Sources...
	main_cleanupunused { a.sendMsg("Main/CleanupUnused") }

	// Close
	main_close { a.sendMsg("Main/Close") }

	// Remove Video
	main_closevideo { a.sendMsg("Main/CloseVideo") }

	// Edit Metadata...
	main_editmetadata { a.sendMsg("Main/EditMetadata") }

	// Escape (deselect all)
	main_escape { a.sendMsg("Main/Escape") }

	// Export
	main_export { a.sendMsg("Main/Export") }

	// Export to Audio File(s)...
	main_exportaudio { a.sendMsg("Main/ExportAudio") }

	// Export to Video File...
	main_exportvideo { a.sendMsg("Main/ExportVideo") }

	// Flush Wastebasket
	main_flushwastebasket { a.sendMsg("Main/FlushWastebasket") }

	// Import Metadata...
	main_importmetadata { a.sendMsg("Main/ImportMetadata") }

	// Metadata
	main_metadata { a.sendMsg("Main/Metadata") }

	// New...
	main_new { a.sendMsg("Main/New") }

	// Open...
	main_open { a.sendMsg("Main/Open") }

	// Open Video...
	main_openvideo { a.sendMsg("Main/OpenVideo") }

	// Quick Snapshot (& keep working on current version) ...
	main_quicksnapshotstay { a.sendMsg("Main/QuickSnapshotStay") }

	// Quick Snapshot (& switch to new version) ...
	main_quicksnapshotswitch { a.sendMsg("Main/QuickSnapshotSwitch") }

	// Recent...
	main_recent { a.sendMsg("Main/Recent") }

	// Rename...
	main_rename { a.sendMsg("Main/Rename") }

	// Save As...
	main_saveas { a.sendMsg("Main/SaveAs") }

	// Save Template...
	main_savetemplate { a.sendMsg("Main/SaveTemplate") }

	// Session|Scripting
	main_scripting { a.sendMsg("Main/Scripting") }

	// Snapshot (& keep working on current version) ...
	main_snapshotstay { a.sendMsg("Main/SnapshotStay") }

	// Snapshot (& switch to new version) ...
	main_snapshotswitch { a.sendMsg("Main/SnapshotSwitch") }

	// Stem export...
	main_stemexport { a.sendMsg("Main/StemExport") }

	// Cancel Solo
	main_cancel_solo { a.sendMsg("Main/cancel-solo") }

	// Close Current Dialog
	main_close_current_dialog { a.sendMsg("Main/close-current-dialog") }

	// Duplicate Tracks/Busses...
	main_duplicate_routes { a.sendMsg("Main/duplicate-routes") }

	// Audio File Format
	main_menu_audiofileformat { a.sendMsg("Main_menu/AudioFileFormat") }

	// Sample Format
	main_menu_audiofileformatdata { a.sendMsg("Main_menu/AudioFileFormatData") }

	// File Type
	main_menu_audiofileformatheader { a.sendMsg("Main_menu/AudioFileFormatHeader") }

	// Clean-up
	main_menu_cleanup { a.sendMsg("Main_menu/Cleanup") }

	// Control Surfaces
	main_menu_controlsurfaces { a.sendMsg("Main_menu/ControlSurfaces") }

	// Denormal Handling
	main_menu_denormals { a.sendMsg("Main_menu/Denormals") }

	// Detach
	main_menu_detachmenu { a.sendMsg("Main_menu/DetachMenu") }

	// Editor
	main_menu_editormenu { a.sendMsg("Main_menu/EditorMenu") }

	// Help
	main_menu_help { a.sendMsg("Main_menu/Help") }

	// Misc. Shortcuts
	main_menu_keymouseactions { a.sendMsg("Main_menu/KeyMouseActions") }

	// Metering
	main_menu_metering { a.sendMsg("Main_menu/Metering") }

	// Fall Off Rate
	main_menu_meteringfalloffrate { a.sendMsg("Main_menu/MeteringFallOffRate") }

	// Hold Time
	main_menu_meteringholdtime { a.sendMsg("Main_menu/MeteringHoldTime") }

	// Mixer
	main_menu_mixermenu { a.sendMsg("Main_menu/MixerMenu") }

	// Plugins
	main_menu_plugins { a.sendMsg("Main_menu/Plugins") }

	// Preferences
	main_menu_prefsmenu { a.sendMsg("Main_menu/PrefsMenu") }

	// Session
	main_menu_session { a.sendMsg("Main_menu/Session") }

	// Sync
	main_menu_sync { a.sendMsg("Main_menu/Sync") }

	// Options
	main_menu_transportoptions { a.sendMsg("Main_menu/TransportOptions") }

	// Window
	main_menu_windowmenu { a.sendMsg("Main_menu/WindowMenu") }

	// Send MMC
	options_sendmmc { a.sendMsg("Options/SendMMC") }

	// Send MTC
	options_sendmtc { a.sendMsg("Options/SendMTC") }

	// Send MIDI Clock
	options_sendmidiclock { a.sendMsg("Options/SendMidiClock") }

	// Use MMC
	options_usemmc { a.sendMsg("Options/UseMMC") }

	// Forward
	transport_forward { a.sendMsg("Transport/Forward") }

	// Forward (Fast)
	transport_forwardfast { a.sendMsg("Transport/ForwardFast") }

	// Forward (Slow)
	transport_forwardslow { a.sendMsg("Transport/ForwardSlow") }

	// Go to End
	transport_gotoend { a.sendMsg("Transport/GotoEnd") }

	// Go to Start
	transport_gotostart { a.sendMsg("Transport/GotoStart") }

	// Go to Wall Clock
	transport_gotowallclock { a.sendMsg("Transport/GotoWallClock") }

	// Go to Zero
	transport_gotozero { a.sendMsg("Transport/GotoZero") }

	// Play Loop Range
	transport_loop { a.sendMsg("Transport/Loop") }

	// Play w/Preroll
	transport_playpreroll { a.sendMsg("Transport/PlayPreroll") }

	// Play Selection
	transport_playselection { a.sendMsg("Transport/PlaySelection") }

	// Enable Record
	transport_record { a.sendMsg("Transport/Record") }

	// Record w/Count-In
	transport_recordcountin { a.sendMsg("Transport/RecordCountIn") }

	// Record w/Preroll
	transport_recordpreroll { a.sendMsg("Transport/RecordPreroll") }

	// Rewind
	transport_rewind { a.sendMsg("Transport/Rewind") }

	// Rewind (Fast)
	transport_rewindfast { a.sendMsg("Transport/RewindFast") }

	// Rewind (Slow)
	transport_rewindslow { a.sendMsg("Transport/RewindSlow") }

	// Roll
	transport_roll { a.sendMsg("Transport/Roll") }

	// All Disk
	transport_sessionmonitordisk { a.sendMsg("Transport/SessionMonitorDisk") }

	// All Input
	transport_sessionmonitorin { a.sendMsg("Transport/SessionMonitorIn") }

	// Stop
	transport_stop { a.sendMsg("Transport/Stop") }

	// Auto Input
	transport_toggleautoinput { a.sendMsg("Transport/ToggleAutoInput") }

	// Auto Play
	transport_toggleautoplay { a.sendMsg("Transport/ToggleAutoPlay") }

	// Auto Return
	transport_toggleautoreturn { a.sendMsg("Transport/ToggleAutoReturn") }

	// Click
	transport_toggleclick { a.sendMsg("Transport/ToggleClick") }

	// Use External Positional Sync Source
	transport_toggleexternalsync { a.sendMsg("Transport/ToggleExternalSync") }

	// Follow Range
	transport_togglefollowedits { a.sendMsg("Transport/ToggleFollowEdits") }

	// Punch In/Out
	transport_togglepunch { a.sendMsg("Transport/TogglePunch") }

	// Punch In
	transport_togglepunchin { a.sendMsg("Transport/TogglePunchIn") }

	// Punch Out
	transport_togglepunchout { a.sendMsg("Transport/TogglePunchOut") }

	// Start/Stop
	transport_toggleroll { a.sendMsg("Transport/ToggleRoll") }

	// Stop and Forget Capture
	transport_togglerollforgetcapture { a.sendMsg("Transport/ToggleRollForgetCapture") }

	// Start/Continue/Stop
	transport_togglerollmaybe { a.sendMsg("Transport/ToggleRollMaybe") }

	// Time Master
	transport_toggletimemaster { a.sendMsg("Transport/ToggleTimeMaster") }

	// Sync Startup to Video
	transport_togglevideosync { a.sendMsg("Transport/ToggleVideoSync") }

	// Transition to Reverse
	transport_transitiontoreverse { a.sendMsg("Transport/TransitionToReverse") }

	// Transition to Roll
	transport_transitiontoroll { a.sendMsg("Transport/TransitionToRoll") }

	// Transport
	transport_transport { a.sendMsg("Transport/Transport") }

	// Go to Start
	transport_alternate_gotostart { a.sendMsg("Transport/alternate-GotoStart") }

	// Start/Stop
	transport_alternate_toggleroll { a.sendMsg("Transport/alternate-ToggleRoll") }

	// Numpad Decimal
	transport_alternate_numpad_decimal { a.sendMsg("Transport/alternate-numpad-decimal") }

	// Start Recording
	transport_alternate_record_roll { a.sendMsg("Transport/alternate-record-roll") }

	// Focus On Clock
	transport_focus_on_clock { a.sendMsg("Transport/focus-on-clock") }

	// Numpad 0
	transport_numpad_0 { a.sendMsg("Transport/numpad-0") }

	// Numpad 1
	transport_numpad_1 { a.sendMsg("Transport/numpad-1") }

	// Numpad 2
	transport_numpad_2 { a.sendMsg("Transport/numpad-2") }

	// Numpad 3
	transport_numpad_3 { a.sendMsg("Transport/numpad-3") }

	// Numpad 4
	transport_numpad_4 { a.sendMsg("Transport/numpad-4") }

	// Numpad 5
	transport_numpad_5 { a.sendMsg("Transport/numpad-5") }

	// Numpad 6
	transport_numpad_6 { a.sendMsg("Transport/numpad-6") }

	// Numpad 7
	transport_numpad_7 { a.sendMsg("Transport/numpad-7") }

	// Numpad 8
	transport_numpad_8 { a.sendMsg("Transport/numpad-8") }

	// Numpad 9
	transport_numpad_9 { a.sendMsg("Transport/numpad-9") }

	// Numpad Decimal
	transport_numpad_decimal { a.sendMsg("Transport/numpad-decimal") }

	// Bars & Beats
	transport_primary_clock_bbt { a.sendMsg("Transport/primary-clock-bbt") }

	// Minutes & Seconds
	transport_primary_clock_minsec { a.sendMsg("Transport/primary-clock-minsec") }

	// Samples
	transport_primary_clock_samples { a.sendMsg("Transport/primary-clock-samples") }

	// Timecode
	transport_primary_clock_timecode { a.sendMsg("Transport/primary-clock-timecode") }

	// Start Recording
	transport_record_roll { a.sendMsg("Transport/record-roll") }

	// Bars & Beats
	transport_secondary_clock_bbt { a.sendMsg("Transport/secondary-clock-bbt") }

	// Minutes & Seconds
	transport_secondary_clock_minsec { a.sendMsg("Transport/secondary-clock-minsec") }

	// Samples
	transport_secondary_clock_samples { a.sendMsg("Transport/secondary-clock-samples") }

	// Timecode
	transport_secondary_clock_timecode { a.sendMsg("Transport/secondary-clock-timecode") }

	// nil
	window_toggle_about { a.sendMsg("Window/toggle-about") }

	// nil
	window_toggle_add_routes { a.sendMsg("Window/toggle-add-routes") }

	// nil
	window_toggle_add_video { a.sendMsg("Window/toggle-add-video") }

	// nil
	window_toggle_audio_connection_manager { a.sendMsg("Window/toggle-audio-connection-manager") }

	// nil
	window_toggle_audio_midi_setup { a.sendMsg("Window/toggle-audio-midi-setup") }

	// nil
	window_toggle_big_clock { a.sendMsg("Window/toggle-big-clock") }

	// nil
	window_toggle_bundle_manager { a.sendMsg("Window/toggle-bundle-manager") }

	// nil
	window_toggle_inspector { a.sendMsg("Window/toggle-inspector") }

	// nil
	window_toggle_key_editor { a.sendMsg("Window/toggle-key-editor") }

	// nil
	window_toggle_locations { a.sendMsg("Window/toggle-locations") }

	// nil
	window_toggle_midi_connection_manager { a.sendMsg("Window/toggle-midi-connection-manager") }

	// nil
	window_toggle_script_manager { a.sendMsg("Window/toggle-script-manager") }

	// nil
	window_toggle_session_options_editor { a.sendMsg("Window/toggle-session-options-editor") }

	// nil
	window_toggle_speaker_config { a.sendMsg("Window/toggle-speaker-config") }

	// nil
	window_toggle_video_export { a.sendMsg("Window/toggle-video-export") }

	// Snap Mode
	editor_snapmode { a.sendMsg("Editor/SnapMode") }

	// Snap to
	editor_snapto { a.sendMsg("Editor/SnapTo") }

	// Show Group Tabs
	editor_togglegrouptabs { a.sendMsg("Editor/ToggleGroupTabs") }

	// Video Monitor
	editor_togglejadeo { a.sendMsg("Editor/ToggleJadeo") }

	// Show Measure Lines
	editor_togglemeasurevisibility { a.sendMsg("Editor/ToggleMeasureVisibility") }

	// Show Summary
	editor_togglesummary { a.sendMsg("Editor/ToggleSummary") }

	// Import PT session
	editor_addexistingptfiles { a.sendMsg("Editor/addExistingPTFiles") }

	// Import to Region List...
	editor_addexternalaudiotoregionlist { a.sendMsg("Editor/addExternalAudioToRegionList") }

	// Redo
	editor_alternate_alternate_redo { a.sendMsg("Editor/alternate-alternate-redo") }

	// Delete
	editor_alternate_editor_delete { a.sendMsg("Editor/alternate-editor-delete") }

	// Redo
	editor_alternate_redo { a.sendMsg("Editor/alternate-redo") }

	// Select All After Edit Point
	editor_alternate_select_all_after_edit_cursor { a.sendMsg("Editor/alternate-select-all-after-edit-cursor") }

	// Select All Before Edit Point
	editor_alternate_select_all_before_edit_cursor { a.sendMsg("Editor/alternate-select-all-before-edit-cursor") }

	// Move to Previous Transient
	editor_alternate_tab_to_transient_backwards { a.sendMsg("Editor/alternate-tab-to-transient-backwards") }

	// Move to Next Transient
	editor_alternate_tab_to_transient_forwards { a.sendMsg("Editor/alternate-tab-to-transient-forwards") }

	// Bring all media into session folder
	editor_bring_into_session { a.sendMsg("Editor/bring-into-session") }

	// Center Edit Point
	editor_center_edit_cursor { a.sendMsg("Editor/center-edit-cursor") }

	// Center Playhead
	editor_center_playhead { a.sendMsg("Editor/center-playhead") }

	// Crop
	editor_crop { a.sendMsg("Editor/crop") }

	// Cycle Edit Mode
	editor_cycle_edit_mode { a.sendMsg("Editor/cycle-edit-mode") }

	// Change Edit Point
	editor_cycle_edit_point { a.sendMsg("Editor/cycle-edit-point") }

	// Change Edit Point Including Marker
	editor_cycle_edit_point_with_marker { a.sendMsg("Editor/cycle-edit-point-with-marker") }

	// Next Snap Mode
	editor_cycle_snap_mode { a.sendMsg("Editor/cycle-snap-mode") }

	// Next Zoom Focus
	editor_cycle_zoom_focus { a.sendMsg("Editor/cycle-zoom-focus") }

	// Deselect All
	editor_deselect_all { a.sendMsg("Editor/deselect-all") }

	// Duplicate
	editor_duplicate { a.sendMsg("Editor/duplicate") }

	// Mouse
	editor_edit_at_mouse { a.sendMsg("Editor/edit-at-mouse") }

	// Playhead
	editor_edit_at_playhead { a.sendMsg("Editor/edit-at-playhead") }

	// Marker
	editor_edit_at_selected_marker { a.sendMsg("Editor/edit-at-selected-marker") }

	// Edit Current Meter
	editor_edit_current_meter { a.sendMsg("Editor/edit-current-meter") }

	// Edit Current Tempo
	editor_edit_current_tempo { a.sendMsg("Editor/edit-current-tempo") }

	// To Next Region End
	editor_edit_cursor_to_next_region_end { a.sendMsg("Editor/edit-cursor-to-next-region-end") }

	// To Next Region Start
	editor_edit_cursor_to_next_region_start { a.sendMsg("Editor/edit-cursor-to-next-region-start") }

	// To Next Region Sync
	editor_edit_cursor_to_next_region_sync { a.sendMsg("Editor/edit-cursor-to-next-region-sync") }

	// To Previous Region End
	editor_edit_cursor_to_previous_region_end { a.sendMsg("Editor/edit-cursor-to-previous-region-end") }

	// To Previous Region Start
	editor_edit_cursor_to_previous_region_start { a.sendMsg("Editor/edit-cursor-to-previous-region-start") }

	// To Previous Region Sync
	editor_edit_cursor_to_previous_region_sync { a.sendMsg("Editor/edit-cursor-to-previous-region-sync") }

	// To Range End
	editor_edit_cursor_to_range_end { a.sendMsg("Editor/edit-cursor-to-range-end") }

	// To Range Start
	editor_edit_cursor_to_range_start { a.sendMsg("Editor/edit-cursor-to-range-start") }

	// Active Mark to Playhead
	editor_edit_to_playhead { a.sendMsg("Editor/edit-to-playhead") }

	// Copy
	editor_editor_copy { a.sendMsg("Editor/editor-copy") }

	// Crop
	editor_editor_crop { a.sendMsg("Editor/editor-crop") }

	// Cut
	editor_editor_cut { a.sendMsg("Editor/editor-cut") }

	// Delete
	editor_editor_delete { a.sendMsg("Editor/editor-delete") }

	// Fade Range Selection
	editor_editor_fade_range { a.sendMsg("Editor/editor-fade-range") }

	// Paste
	editor_editor_paste { a.sendMsg("Editor/editor-paste") }

	// Separate
	editor_editor_separate { a.sendMsg("Editor/editor-separate") }

	// Expand Track Height
	editor_expand_tracks { a.sendMsg("Editor/expand-tracks") }

	// Export Audio
	editor_export_audio { a.sendMsg("Editor/export-audio") }

	// Export Range
	editor_export_range { a.sendMsg("Editor/export-range") }

	// Fit Selection (Vertical)
	editor_fit_selection { a.sendMsg("Editor/fit-selection") }

	// Fit 16 Tracks
	editor_fit_16_tracks { a.sendMsg("Editor/fit_16_tracks") }

	// Fit 1 Track
	editor_fit_1_track { a.sendMsg("Editor/fit_1_track") }

	// Fit 2 Tracks
	editor_fit_2_tracks { a.sendMsg("Editor/fit_2_tracks") }

	// Fit 32 Tracks
	editor_fit_32_tracks { a.sendMsg("Editor/fit_32_tracks") }

	// Fit 4 Tracks
	editor_fit_4_tracks { a.sendMsg("Editor/fit_4_tracks") }

	// Fit 8 Tracks
	editor_fit_8_tracks { a.sendMsg("Editor/fit_8_tracks") }

	// Fit All Tracks
	editor_fit_all_tracks { a.sendMsg("Editor/fit_all_tracks") }

	// Locate to Mark 1
	editor_goto_mark_1 { a.sendMsg("Editor/goto-mark-1") }

	// Locate to Mark 2
	editor_goto_mark_2 { a.sendMsg("Editor/goto-mark-2") }

	// Locate to Mark 3
	editor_goto_mark_3 { a.sendMsg("Editor/goto-mark-3") }

	// Locate to Mark 4
	editor_goto_mark_4 { a.sendMsg("Editor/goto-mark-4") }

	// Locate to Mark 5
	editor_goto_mark_5 { a.sendMsg("Editor/goto-mark-5") }

	// Locate to Mark 6
	editor_goto_mark_6 { a.sendMsg("Editor/goto-mark-6") }

	// Locate to Mark 7
	editor_goto_mark_7 { a.sendMsg("Editor/goto-mark-7") }

	// Locate to Mark 8
	editor_goto_mark_8 { a.sendMsg("Editor/goto-mark-8") }

	// Locate to Mark 9
	editor_goto_mark_9 { a.sendMsg("Editor/goto-mark-9") }

	// Go to View 1
	editor_goto_visual_state_1 { a.sendMsg("Editor/goto-visual-state-1") }

	// Go to View 10
	editor_goto_visual_state_10 { a.sendMsg("Editor/goto-visual-state-10") }

	// Go to View 11
	editor_goto_visual_state_11 { a.sendMsg("Editor/goto-visual-state-11") }

	// Go to View 12
	editor_goto_visual_state_12 { a.sendMsg("Editor/goto-visual-state-12") }

	// Go to View 2
	editor_goto_visual_state_2 { a.sendMsg("Editor/goto-visual-state-2") }

	// Go to View 3
	editor_goto_visual_state_3 { a.sendMsg("Editor/goto-visual-state-3") }

	// Go to View 4
	editor_goto_visual_state_4 { a.sendMsg("Editor/goto-visual-state-4") }

	// Go to View 5
	editor_goto_visual_state_5 { a.sendMsg("Editor/goto-visual-state-5") }

	// Go to View 6
	editor_goto_visual_state_6 { a.sendMsg("Editor/goto-visual-state-6") }

	// Go to View 7
	editor_goto_visual_state_7 { a.sendMsg("Editor/goto-visual-state-7") }

	// Go to View 8
	editor_goto_visual_state_8 { a.sendMsg("Editor/goto-visual-state-8") }

	// Go to View 9
	editor_goto_visual_state_9 { a.sendMsg("Editor/goto-visual-state-9") }

	// Import from Session
	editor_importfromsession { a.sendMsg("Editor/importFromSession") }

	// Insert Time
	editor_insert_time { a.sendMsg("Editor/insert-time") }

	// Invert Selection
	editor_invert_selection { a.sendMsg("Editor/invert-selection") }

	// Session|Lock
	editor_lock { a.sendMsg("Editor/lock") }

	// Play Selected Regions
	editor_main_menu_play_selected_regions { a.sendMsg("Editor/main-menu-play-selected-regions") }

	// Move Range End to Next Region Boundary
	editor_move_range_end_to_next_region_boundary { a.sendMsg("Editor/move-range-end-to-next-region-boundary") }

	// Move Range End to Previous Region Boundary
	editor_move_range_end_to_previous_region_boundary { a.sendMsg("Editor/move-range-end-to-previous-region-boundary") }

	// Move Range Start to Next Region Boundary
	editor_move_range_start_to_next_region_boundary { a.sendMsg("Editor/move-range-start-to-next-region-boundary") }

	// Move Range Start to Previous Region Boundary
	editor_move_range_start_to_previous_region_boundary { a.sendMsg("Editor/move-range-start-to-previous-region-boundary") }

	// Move Selected Tracks Down
	editor_move_selected_tracks_down { a.sendMsg("Editor/move-selected-tracks-down") }

	// Move Selected Tracks Up
	editor_move_selected_tracks_up { a.sendMsg("Editor/move-selected-tracks-up") }

	// Multi-Duplicate...
	editor_multi_duplicate { a.sendMsg("Editor/multi-duplicate") }

	// Next Snap Choice
	editor_next_snap_choice { a.sendMsg("Editor/next-snap-choice") }

	// Next Musical Snap Choice
	editor_next_snap_choice_music_only { a.sendMsg("Editor/next-snap-choice-music-only") }

	// Play Edit Range
	editor_play_edit_range { a.sendMsg("Editor/play-edit-range") }

	// Play from Edit Point
	editor_play_from_edit_point { a.sendMsg("Editor/play-from-edit-point") }

	// Play from Edit Point and Return
	editor_play_from_edit_point_and_return { a.sendMsg("Editor/play-from-edit-point-and-return") }

	// Playhead to Active Mark
	editor_playhead_to_edit { a.sendMsg("Editor/playhead-to-edit") }

	// Playhead to Next Region Boundary
	editor_playhead_to_next_region_boundary { a.sendMsg("Editor/playhead-to-next-region-boundary") }

	// Playhead to Next Region Boundary (No Track Selection)
	editor_playhead_to_next_region_boundary_noselection { a.sendMsg("Editor/playhead-to-next-region-boundary-noselection") }

	// Playhead to Next Region End
	editor_playhead_to_next_region_end { a.sendMsg("Editor/playhead-to-next-region-end") }

	// Playhead to Next Region Start
	editor_playhead_to_next_region_start { a.sendMsg("Editor/playhead-to-next-region-start") }

	// Playhead to Next Region Sync
	editor_playhead_to_next_region_sync { a.sendMsg("Editor/playhead-to-next-region-sync") }

	// Playhead to Previous Region Boundary
	editor_playhead_to_previous_region_boundary { a.sendMsg("Editor/playhead-to-previous-region-boundary") }

	// Playhead to Previous Region Boundary (No Track Selection)
	editor_playhead_to_previous_region_boundary_noselection { a.sendMsg("Editor/playhead-to-previous-region-boundary-noselection") }

	// Playhead to Previous Region End
	editor_playhead_to_previous_region_end { a.sendMsg("Editor/playhead-to-previous-region-end") }

	// Playhead to Previous Region Start
	editor_playhead_to_previous_region_start { a.sendMsg("Editor/playhead-to-previous-region-start") }

	// Playhead to Previous Region Sync
	editor_playhead_to_previous_region_sync { a.sendMsg("Editor/playhead-to-previous-region-sync") }

	// Playhead to Range End
	editor_playhead_to_range_end { a.sendMsg("Editor/playhead-to-range-end") }

	// Playhead to Range Start
	editor_playhead_to_range_start { a.sendMsg("Editor/playhead-to-range-start") }

	// Previous Snap Choice
	editor_prev_snap_choice { a.sendMsg("Editor/prev-snap-choice") }

	// Previous Musical Snap Choice
	editor_prev_snap_choice_music_only { a.sendMsg("Editor/prev-snap-choice-music-only") }

	// Quantize
	editor_quantize { a.sendMsg("Editor/quantize") }

	// Redo
	editor_redo { a.sendMsg("Editor/redo") }

	// Redo Selection Change
	editor_redo_last_selection_op { a.sendMsg("Editor/redo-last-selection-op") }

	// Remove Last Capture
	editor_remove_last_capture { a.sendMsg("Editor/remove-last-capture") }

	// Remove Time
	editor_remove_time { a.sendMsg("Editor/remove-time") }

	// Remove
	editor_remove_track { a.sendMsg("Editor/remove-track") }

	// Save View 1
	editor_save_visual_state_1 { a.sendMsg("Editor/save-visual-state-1") }

	// Save View 10
	editor_save_visual_state_10 { a.sendMsg("Editor/save-visual-state-10") }

	// Save View 11
	editor_save_visual_state_11 { a.sendMsg("Editor/save-visual-state-11") }

	// Save View 12
	editor_save_visual_state_12 { a.sendMsg("Editor/save-visual-state-12") }

	// Save View 2
	editor_save_visual_state_2 { a.sendMsg("Editor/save-visual-state-2") }

	// Save View 3
	editor_save_visual_state_3 { a.sendMsg("Editor/save-visual-state-3") }

	// Save View 4
	editor_save_visual_state_4 { a.sendMsg("Editor/save-visual-state-4") }

	// Save View 5
	editor_save_visual_state_5 { a.sendMsg("Editor/save-visual-state-5") }

	// Save View 6
	editor_save_visual_state_6 { a.sendMsg("Editor/save-visual-state-6") }

	// Save View 7
	editor_save_visual_state_7 { a.sendMsg("Editor/save-visual-state-7") }

	// Save View 8
	editor_save_visual_state_8 { a.sendMsg("Editor/save-visual-state-8") }

	// Save View 9
	editor_save_visual_state_9 { a.sendMsg("Editor/save-visual-state-9") }

	// Custom Action #1
	editor_script_action_1 { a.sendMsg("Editor/script-action-1") }

	// Custom Action #2
	editor_script_action_2 { a.sendMsg("Editor/script-action-2") }

	// Custom Action #3
	editor_script_action_3 { a.sendMsg("Editor/script-action-3") }

	// Custom Action #4
	editor_script_action_4 { a.sendMsg("Editor/script-action-4") }

	// Custom Action #5
	editor_script_action_5 { a.sendMsg("Editor/script-action-5") }

	// Custom Action #6
	editor_script_action_6 { a.sendMsg("Editor/script-action-6") }

	// Custom Action #7
	editor_script_action_7 { a.sendMsg("Editor/script-action-7") }

	// Custom Action #8
	editor_script_action_8 { a.sendMsg("Editor/script-action-8") }

	// Custom Action #9
	editor_script_action_9 { a.sendMsg("Editor/script-action-9") }

	// Scroll Backward
	editor_scroll_backward { a.sendMsg("Editor/scroll-backward") }

	// Scroll Forward
	editor_scroll_forward { a.sendMsg("Editor/scroll-forward") }

	// Playhead Backward
	editor_scroll_playhead_backward { a.sendMsg("Editor/scroll-playhead-backward") }

	// Playhead Forward
	editor_scroll_playhead_forward { a.sendMsg("Editor/scroll-playhead-forward") }

	// Scroll Tracks Down
	editor_scroll_tracks_down { a.sendMsg("Editor/scroll-tracks-down") }

	// Scroll Tracks Up
	editor_scroll_tracks_up { a.sendMsg("Editor/scroll-tracks-up") }

	// Select All After Edit Point
	editor_select_all_after_edit_cursor { a.sendMsg("Editor/select-all-after-edit-cursor") }

	// Select All Before Edit Point
	editor_select_all_before_edit_cursor { a.sendMsg("Editor/select-all-before-edit-cursor") }

	// Select All Overlapping Edit Range
	editor_select_all_between_cursors { a.sendMsg("Editor/select-all-between-cursors") }

	// Select All in Loop Range
	editor_select_all_in_loop_range { a.sendMsg("Editor/select-all-in-loop-range") }

	// Select All in Punch Range
	editor_select_all_in_punch_range { a.sendMsg("Editor/select-all-in-punch-range") }

	// Select All Objects
	editor_select_all_objects { a.sendMsg("Editor/select-all-objects") }

	// Select All Tracks
	editor_select_all_tracks { a.sendMsg("Editor/select-all-tracks") }

	// Select All Inside Edit Range
	editor_select_all_within_cursors { a.sendMsg("Editor/select-all-within-cursors") }

	// Set Range to Selected Regions
	editor_select_from_regions { a.sendMsg("Editor/select-from-regions") }

	// Set Range to Loop Range
	editor_select_loop_range { a.sendMsg("Editor/select-loop-range") }

	// Select Next Track or Bus
	editor_select_next_route { a.sendMsg("Editor/select-next-route") }

	// Select Previous Track or Bus
	editor_select_prev_route { a.sendMsg("Editor/select-prev-route") }

	// Set Range to Punch Range
	editor_select_punch_range { a.sendMsg("Editor/select-punch-range") }

	// Select Edit Range
	editor_select_range_between_cursors { a.sendMsg("Editor/select-range-between-cursors") }

	// To Next Region Boundary
	editor_selected_marker_to_next_region_boundary { a.sendMsg("Editor/selected-marker-to-next-region-boundary") }

	// To Next Region Boundary (No Track Selection)
	editor_selected_marker_to_next_region_boundary_noselection { a.sendMsg("Editor/selected-marker-to-next-region-boundary-noselection") }

	// To Previous Region Boundary
	editor_selected_marker_to_previous_region_boundary { a.sendMsg("Editor/selected-marker-to-previous-region-boundary") }

	// To Previous Region Boundary (No Track Selection)
	editor_selected_marker_to_previous_region_boundary_noselection { a.sendMsg("Editor/selected-marker-to-previous-region-boundary-noselection") }

	// Separate Using Loop Range
	editor_separate_from_loop { a.sendMsg("Editor/separate-from-loop") }

	// Separate Using Punch Range
	editor_separate_from_punch { a.sendMsg("Editor/separate-from-punch") }

	// Set Auto Punch In/Out from Playhead
	editor_set_auto_punch_range { a.sendMsg("Editor/set-auto-punch-range") }

	// EditMode|Lock
	editor_set_edit_lock { a.sendMsg("Editor/set-edit-lock") }

	// Active Marker to Mouse
	editor_set_edit_point { a.sendMsg("Editor/set-edit-point") }

	// Ripple
	editor_set_edit_ripple { a.sendMsg("Editor/set-edit-ripple") }

	// Slide
	editor_set_edit_slide { a.sendMsg("Editor/set-edit-slide") }

	// Set Loop from Selection
	editor_set_loop_from_edit_range { a.sendMsg("Editor/set-loop-from-edit-range") }

	// Playhead to Mouse
	editor_set_playhead { a.sendMsg("Editor/set-playhead") }

	// Set Punch from Selection
	editor_set_punch_from_edit_range { a.sendMsg("Editor/set-punch-from-edit-range") }

	// Set Session Start/End from Selection
	editor_set_session_from_edit_range { a.sendMsg("Editor/set-session-from-edit-range") }

	// Set Tempo from Edit Range = Bar
	editor_set_tempo_from_edit_range { a.sendMsg("Editor/set-tempo-from-edit-range") }

	// Show Editor List
	editor_show_editor_list { a.sendMsg("Editor/show-editor-list") }

	// Show Editor Mixer
	editor_show_editor_mixer { a.sendMsg("Editor/show-editor-mixer") }

	// Show Marker Lines
	editor_show_marker_lines { a.sendMsg("Editor/show-marker-lines") }

	// Shrink Track Height
	editor_shrink_tracks { a.sendMsg("Editor/shrink-tracks") }

	// Magnetic
	editor_snap_magnetic { a.sendMsg("Editor/snap-magnetic") }

	// Grid
	editor_snap_normal { a.sendMsg("Editor/snap-normal") }

	// No Grid
	editor_snap_off { a.sendMsg("Editor/snap-off") }

	// Sound Selected MIDI Notes
	editor_sound_midi_notes { a.sendMsg("Editor/sound-midi-notes") }

	// Split/Separate
	editor_split_region { a.sendMsg("Editor/split-region") }

	// Step Mouse Mode
	editor_step_mouse_mode { a.sendMsg("Editor/step-mouse-mode") }

	// Step Tracks Down
	editor_step_tracks_down { a.sendMsg("Editor/step-tracks-down") }

	// Step Tracks Up
	editor_step_tracks_up { a.sendMsg("Editor/step-tracks-up") }

	// Move to Previous Transient
	editor_tab_to_transient_backwards { a.sendMsg("Editor/tab-to-transient-backwards") }

	// Move to Next Transient
	editor_tab_to_transient_forwards { a.sendMsg("Editor/tab-to-transient-forwards") }

	// Zoom In
	editor_temporal_zoom_in { a.sendMsg("Editor/temporal-zoom-in") }

	// Zoom Out
	editor_temporal_zoom_out { a.sendMsg("Editor/temporal-zoom-out") }

	// Follow Playhead
	editor_toggle_follow_playhead { a.sendMsg("Editor/toggle-follow-playhead") }

	// Log
	editor_toggle_log_window { a.sendMsg("Editor/toggle-log-window") }

	// Toggle MIDI Input Active for Editor-Selected Tracks/Busses
	editor_toggle_midi_input_active { a.sendMsg("Editor/toggle-midi-input-active") }

	// Use Skip Ranges
	editor_toggle_skip_playback { a.sendMsg("Editor/toggle-skip-playback") }

	// Stationary Playhead
	editor_toggle_stationary_playhead { a.sendMsg("Editor/toggle-stationary-playhead") }

	// Toggle Active
	editor_toggle_track_active { a.sendMsg("Editor/toggle-track-active") }

	// Frame number
	editor_toggle_vmon_frame { a.sendMsg("Editor/toggle-vmon-frame") }

	// Fullscreen
	editor_toggle_vmon_fullscreen { a.sendMsg("Editor/toggle-vmon-fullscreen") }

	// Letterbox
	editor_toggle_vmon_letterbox { a.sendMsg("Editor/toggle-vmon-letterbox") }

	// Always on Top
	editor_toggle_vmon_ontop { a.sendMsg("Editor/toggle-vmon-ontop") }

	// Timecode Background
	editor_toggle_vmon_osdbg { a.sendMsg("Editor/toggle-vmon-osdbg") }

	// Timecode
	editor_toggle_vmon_timecode { a.sendMsg("Editor/toggle-vmon-timecode") }

	// Toggle Zoom State
	editor_toggle_zoom { a.sendMsg("Editor/toggle-zoom") }

	// Large
	editor_track_height_large { a.sendMsg("Editor/track-height-large") }

	// Larger
	editor_track_height_larger { a.sendMsg("Editor/track-height-larger") }

	// Largest
	editor_track_height_largest { a.sendMsg("Editor/track-height-largest") }

	// Normal
	editor_track_height_normal { a.sendMsg("Editor/track-height-normal") }

	// Small
	editor_track_height_small { a.sendMsg("Editor/track-height-small") }

	// Toggle Mute
	editor_track_mute_toggle { a.sendMsg("Editor/track-mute-toggle") }

	// Toggle Record Enable
	editor_track_record_enable_toggle { a.sendMsg("Editor/track-record-enable-toggle") }

	// Toggle Solo Isolate
	editor_track_solo_isolate_toggle { a.sendMsg("Editor/track-solo-isolate-toggle") }

	// Toggle Solo
	editor_track_solo_toggle { a.sendMsg("Editor/track-solo-toggle") }

	// Command|Undo
	editor_undo { a.sendMsg("Editor/undo") }

	// Undo Selection Change
	editor_undo_last_selection_op { a.sendMsg("Editor/undo-last-selection-op") }

	// Zoom to Selection
	editor_zoom_to_selection { a.sendMsg("Editor/zoom-to-selection") }

	// Zoom to Selection (Horizontal)
	editor_zoom_to_selection_horiz { a.sendMsg("Editor/zoom-to-selection-horiz") }

	// Zoom to Session
	editor_zoom_to_session { a.sendMsg("Editor/zoom-to-session") }

	// Original Size
	editor_zoom_vmon_100 { a.sendMsg("Editor/zoom-vmon-100") }

	// Zoom to 100 ms
	editor_zoom_100_ms { a.sendMsg("Editor/zoom_100_ms") }

	// Zoom to 10 min
	editor_zoom_10_min { a.sendMsg("Editor/zoom_10_min") }

	// Zoom to 10 ms
	editor_zoom_10_ms { a.sendMsg("Editor/zoom_10_ms") }

	// Zoom to 10 sec
	editor_zoom_10_sec { a.sendMsg("Editor/zoom_10_sec") }

	// Zoom to 1 min
	editor_zoom_1_min { a.sendMsg("Editor/zoom_1_min") }

	// Zoom to 1 sec
	editor_zoom_1_sec { a.sendMsg("Editor/zoom_1_sec") }

	// Zoom to 5 min
	editor_zoom_5_min { a.sendMsg("Editor/zoom_5_min") }

	// Align
	editormenu_alignmenu { a.sendMsg("EditorMenu/AlignMenu") }

	// Autoconnect
	editormenu_autoconnect { a.sendMsg("EditorMenu/Autoconnect") }

	// Crossfades
	editormenu_crossfades { a.sendMsg("EditorMenu/Crossfades") }

	// Edit
	editormenu_edit { a.sendMsg("EditorMenu/Edit") }

	// Move Selected Marker
	editormenu_editcursormovementoptions { a.sendMsg("EditorMenu/EditCursorMovementOptions") }

	// Edit Point
	editormenu_editpointmenu { a.sendMsg("EditorMenu/EditPointMenu") }

	// Select Range Operations
	editormenu_editselectrangeoptions { a.sendMsg("EditorMenu/EditSelectRangeOptions") }

	// Select Regions
	editormenu_editselectregionoptions { a.sendMsg("EditorMenu/EditSelectRegionOptions") }

	// Fade
	editormenu_fademenu { a.sendMsg("EditorMenu/FadeMenu") }

	// Latch
	editormenu_latchmenu { a.sendMsg("EditorMenu/LatchMenu") }

	// Link
	editormenu_link { a.sendMsg("EditorMenu/Link") }

	// Locate to Markers
	editormenu_locatetomarker { a.sendMsg("EditorMenu/LocateToMarker") }

	// Lua Scripts
	editormenu_luascripts { a.sendMsg("EditorMenu/LuaScripts") }

	// MIDI Options
	editormenu_midi { a.sendMsg("EditorMenu/MIDI") }

	// Markers
	editormenu_markermenu { a.sendMsg("EditorMenu/MarkerMenu") }

	// Meter falloff
	editormenu_meterfalloff { a.sendMsg("EditorMenu/MeterFalloff") }

	// Meter hold
	editormenu_meterhold { a.sendMsg("EditorMenu/MeterHold") }

	// Misc Options
	editormenu_miscoptions { a.sendMsg("EditorMenu/MiscOptions") }

	// Monitoring
	editormenu_monitoring { a.sendMsg("EditorMenu/Monitoring") }

	// Active Mark
	editormenu_moveactivemarkmenu { a.sendMsg("EditorMenu/MoveActiveMarkMenu") }

	// Playhead
	editormenu_moveplayheadmenu { a.sendMsg("EditorMenu/MovePlayHeadMenu") }

	// Play
	editormenu_playmenu { a.sendMsg("EditorMenu/PlayMenu") }

	// Primary Clock
	editormenu_primaryclockmenu { a.sendMsg("EditorMenu/PrimaryClockMenu") }

	// Pullup / Pulldown
	editormenu_pullup { a.sendMsg("EditorMenu/Pullup") }

	// Region operations
	editormenu_regioneditops { a.sendMsg("EditorMenu/RegionEditOps") }

	// Gain
	editormenu_regiongainmenu { a.sendMsg("EditorMenu/RegionGainMenu") }

	// Region
	editormenu_regionmenu { a.sendMsg("EditorMenu/RegionMenu") }

	// Duplicate
	editormenu_regionmenuduplicate { a.sendMsg("EditorMenu/RegionMenuDuplicate") }

	// Edit
	editormenu_regionmenuedit { a.sendMsg("EditorMenu/RegionMenuEdit") }

	// Fades
	editormenu_regionmenufades { a.sendMsg("EditorMenu/RegionMenuFades") }

	// Gain
	editormenu_regionmenugain { a.sendMsg("EditorMenu/RegionMenuGain") }

	// Layering
	editormenu_regionmenulayering { a.sendMsg("EditorMenu/RegionMenuLayering") }

	// MIDI
	editormenu_regionmenumidi { a.sendMsg("EditorMenu/RegionMenuMIDI") }

	// Position
	editormenu_regionmenuposition { a.sendMsg("EditorMenu/RegionMenuPosition") }

	// Ranges
	editormenu_regionmenuranges { a.sendMsg("EditorMenu/RegionMenuRanges") }

	// Trim
	editormenu_regionmenutrim { a.sendMsg("EditorMenu/RegionMenuTrim") }

	// Rulers
	editormenu_rulermenu { a.sendMsg("EditorMenu/RulerMenu") }

	// Views
	editormenu_savedviewmenu { a.sendMsg("EditorMenu/SavedViewMenu") }

	// Scroll
	editormenu_scrollmenu { a.sendMsg("EditorMenu/ScrollMenu") }

	// Secondary Clock
	editormenu_secondaryclockmenu { a.sendMsg("EditorMenu/SecondaryClockMenu") }

	// Select
	editormenu_select { a.sendMsg("EditorMenu/Select") }

	// Select
	editormenu_selectmenu { a.sendMsg("EditorMenu/SelectMenu") }

	// Separate
	editormenu_separatemenu { a.sendMsg("EditorMenu/SeparateMenu") }

	// Loop
	editormenu_setloopmenu { a.sendMsg("EditorMenu/SetLoopMenu") }

	// Punch
	editormenu_setpunchmenu { a.sendMsg("EditorMenu/SetPunchMenu") }

	// Solo
	editormenu_solo { a.sendMsg("EditorMenu/Solo") }

	// Subframes
	editormenu_subframes { a.sendMsg("EditorMenu/Subframes") }

	// Sync
	editormenu_syncmenu { a.sendMsg("EditorMenu/SyncMenu") }

	// Tempo
	editormenu_tempomenu { a.sendMsg("EditorMenu/TempoMenu") }

	// Timecode fps
	editormenu_timecode { a.sendMsg("EditorMenu/Timecode") }

	// Tools
	editormenu_tools { a.sendMsg("EditorMenu/Tools") }

	// Height
	editormenu_trackheightmenu { a.sendMsg("EditorMenu/TrackHeightMenu") }

	// Track
	editormenu_trackmenu { a.sendMsg("EditorMenu/TrackMenu") }

	// Video Monitor
	editormenu_videomonitormenu { a.sendMsg("EditorMenu/VideoMonitorMenu") }

	// View
	editormenu_view { a.sendMsg("EditorMenu/View") }

	// Zoom Focus
	editormenu_zoomfocus { a.sendMsg("EditorMenu/ZoomFocus") }

	// Zoom Focus
	editormenu_zoomfocusmenu { a.sendMsg("EditorMenu/ZoomFocusMenu") }

	// Zoom
	editormenu_zoommenu { a.sendMsg("EditorMenu/ZoomMenu") }

	// Audition Tool
	mousemode_set_mouse_mode_audition { a.sendMsg("MouseMode/set-mouse-mode-audition") }

	// Content Tool
	mousemode_set_mouse_mode_content { a.sendMsg("MouseMode/set-mouse-mode-content") }

	// Cut Tool
	mousemode_set_mouse_mode_cut { a.sendMsg("MouseMode/set-mouse-mode-cut") }

	// Note Drawing Tool
	mousemode_set_mouse_mode_draw { a.sendMsg("MouseMode/set-mouse-mode-draw") }

	// Object Tool
	mousemode_set_mouse_mode_object { a.sendMsg("MouseMode/set-mouse-mode-object") }

	// Smart Object Mode
	mousemode_set_mouse_mode_object_range { a.sendMsg("MouseMode/set-mouse-mode-object-range") }

	// Range Tool
	mousemode_set_mouse_mode_range { a.sendMsg("MouseMode/set-mouse-mode-range") }

	// Time FX Tool
	mousemode_set_mouse_mode_timefx { a.sendMsg("MouseMode/set-mouse-mode-timefx") }

	// Add Single Range Marker
	region_add_range_marker_from_region { a.sendMsg("Region/add-range-marker-from-region") }

	// Add Range Marker Per Region
	region_add_range_markers_from_region { a.sendMsg("Region/add-range-markers-from-region") }

	// Align End
	region_align_regions_end { a.sendMsg("Region/align-regions-end") }

	// Align End Relative
	region_align_regions_end_relative { a.sendMsg("Region/align-regions-end-relative") }

	// Align Start
	region_align_regions_start { a.sendMsg("Region/align-regions-start") }

	// Align Start Relative
	region_align_regions_start_relative { a.sendMsg("Region/align-regions-start-relative") }

	// Align Sync
	region_align_regions_sync { a.sendMsg("Region/align-regions-sync") }

	// Align Sync Relative
	region_align_regions_sync_relative { a.sendMsg("Region/align-regions-sync-relative") }

	// Nudge Earlier
	region_alternate_nudge_backward { a.sendMsg("Region/alternate-nudge-backward") }

	// Nudge Later
	region_alternate_nudge_forward { a.sendMsg("Region/alternate-nudge-forward") }

	// Set Fade In Length
	region_alternate_set_fade_in_length { a.sendMsg("Region/alternate-set-fade-in-length") }

	// Set Fade Out Length
	region_alternate_set_fade_out_length { a.sendMsg("Region/alternate-set-fade-out-length") }

	// Boost Gain
	region_boost_region_gain { a.sendMsg("Region/boost-region-gain") }

	// Bounce (with processing)
	region_bounce_regions_processed { a.sendMsg("Region/bounce-regions-processed") }

	// Bounce (without processing)
	region_bounce_regions_unprocessed { a.sendMsg("Region/bounce-regions-unprocessed") }

	// Choose Top...
	region_choose_top_region { a.sendMsg("Region/choose-top-region") }

	// Choose Top...
	region_choose_top_region_context_menu { a.sendMsg("Region/choose-top-region-context-menu") }

	// Close Gaps
	region_close_region_gaps { a.sendMsg("Region/close-region-gaps") }

	// Combine
	region_combine_regions { a.sendMsg("Region/combine-regions") }

	// Cut Gain
	region_cut_region_gain { a.sendMsg("Region/cut-region-gain") }

	// Duplicate
	region_duplicate_region { a.sendMsg("Region/duplicate-region") }

	// Export...
	region_export_region { a.sendMsg("Region/export-region") }

	// Unlink from other copies
	region_fork_region { a.sendMsg("Region/fork-region") }

	// Insert Patch Change...
	region_insert_patch_change { a.sendMsg("Region/insert-patch-change") }

	// Insert Patch Change...
	region_insert_patch_change_context { a.sendMsg("Region/insert-patch-change-context") }

	// Insert Region from Region List
	region_insert_region_from_region_list { a.sendMsg("Region/insert-region-from-region-list") }

	// Legatize
	region_legatize_region { a.sendMsg("Region/legatize-region") }

	// Loop
	region_loop_region { a.sendMsg("Region/loop-region") }

	// Loudness Analysis...
	region_loudness_analyze_region { a.sendMsg("Region/loudness-analyze-region") }

	// Lower
	region_lower_region { a.sendMsg("Region/lower-region") }

	// Lower to Bottom
	region_lower_region_to_bottom { a.sendMsg("Region/lower-region-to-bottom") }

	// Multi-Duplicate...
	region_multi_duplicate_region { a.sendMsg("Region/multi-duplicate-region") }

	// Move to Original Position
	region_naturalize_region { a.sendMsg("Region/naturalize-region") }

	// Normalize...
	region_normalize_region { a.sendMsg("Region/normalize-region") }

	// Nudge Earlier
	region_nudge_backward { a.sendMsg("Region/nudge-backward") }

	// Nudge Earlier by Capture Offset
	region_nudge_backward_by_capture_offset { a.sendMsg("Region/nudge-backward-by-capture-offset") }

	// Nudge Later
	region_nudge_forward { a.sendMsg("Region/nudge-forward") }

	// Nudge Later by Capture Offset
	region_nudge_forward_by_capture_offset { a.sendMsg("Region/nudge-forward-by-capture-offset") }

	// Pitch Shift...
	region_pitch_shift_region { a.sendMsg("Region/pitch-shift-region") }

	// Place Transient
	region_place_transient { a.sendMsg("Region/place-transient") }

	// Play selected Regions
	region_play_selected_regions { a.sendMsg("Region/play-selected-regions") }

	// Quantize...
	region_quantize_region { a.sendMsg("Region/quantize-region") }

	// Raise
	region_raise_region { a.sendMsg("Region/raise-region") }

	// Raise to Top
	region_raise_region_to_top { a.sendMsg("Region/raise-region-to-top") }

	// Fill Track
	region_region_fill_track { a.sendMsg("Region/region-fill-track") }

	// Remove Overlap
	region_remove_overlap { a.sendMsg("Region/remove-overlap") }

	// Remove
	region_remove_region { a.sendMsg("Region/remove-region") }

	// Remove Sync
	region_remove_region_sync { a.sendMsg("Region/remove-region-sync") }

	// Rename...
	region_rename_region { a.sendMsg("Region/rename-region") }

	// Reset Gain
	region_reset_region_gain { a.sendMsg("Region/reset-region-gain") }

	// Reset Envelope
	region_reset_region_gain_envelopes { a.sendMsg("Region/reset-region-gain-envelopes") }

	// Reset Gain
	region_reset_region_scale_amplitude { a.sendMsg("Region/reset-region-scale-amplitude") }

	// Reverse
	region_reverse_region { a.sendMsg("Region/reverse-region") }

	// Separate Under
	region_separate_under_region { a.sendMsg("Region/separate-under-region") }

	// Sequence Regions
	region_sequence_regions { a.sendMsg("Region/sequence-regions") }

	// Set Fade In Length
	region_set_fade_in_length { a.sendMsg("Region/set-fade-in-length") }

	// Set Fade Out Length
	region_set_fade_out_length { a.sendMsg("Region/set-fade-out-length") }

	// Set Loop Range
	region_set_loop_from_region { a.sendMsg("Region/set-loop-from-region") }

	// Set Punch
	region_set_punch_from_region { a.sendMsg("Region/set-punch-from-region") }

	// Set Sync Position
	region_set_region_sync_position { a.sendMsg("Region/set-region-sync-position") }

	// Set Range Selection
	region_set_selection_from_region { a.sendMsg("Region/set-selection-from-region") }

	// Set Tempo from Region = Bar
	region_set_tempo_from_region { a.sendMsg("Region/set-tempo-from-region") }

	// List Editor...
	region_show_region_list_editor { a.sendMsg("Region/show-region-list-editor") }

	// Properties...
	region_show_region_properties { a.sendMsg("Region/show-region-properties") }

	// Rhythm Ferret...
	region_show_rhythm_ferret { a.sendMsg("Region/show-rhythm-ferret") }

	// Snap Position to Grid
	region_snap_regions_to_grid { a.sendMsg("Region/snap-regions-to-grid") }

	// Spectral Analysis...
	region_spectral_analyze_region { a.sendMsg("Region/spectral-analyze-region") }

	// Make Mono Regions
	region_split_multichannel_region { a.sendMsg("Region/split-multichannel-region") }

	// Split at Percussion Onsets
	region_split_region_at_transients { a.sendMsg("Region/split-region-at-transients") }

	// Strip Silence...
	region_strip_region_silence { a.sendMsg("Region/strip-region-silence") }

	// Opaque
	region_toggle_opaque_region { a.sendMsg("Region/toggle-opaque-region") }

	// Fade In
	region_toggle_region_fade_in { a.sendMsg("Region/toggle-region-fade-in") }

	// Fade Out
	region_toggle_region_fade_out { a.sendMsg("Region/toggle-region-fade-out") }

	// Fades
	region_toggle_region_fades { a.sendMsg("Region/toggle-region-fades") }

	// Envelope Active
	region_toggle_region_gain_envelope_active { a.sendMsg("Region/toggle-region-gain-envelope-active") }

	// Lock
	region_toggle_region_lock { a.sendMsg("Region/toggle-region-lock") }

	// Glue to Bars and Beats
	region_toggle_region_lock_style { a.sendMsg("Region/toggle-region-lock-style") }

	// Mute
	region_toggle_region_mute { a.sendMsg("Region/toggle-region-mute") }

	// Lock to Video
	region_toggle_region_video_lock { a.sendMsg("Region/toggle-region-video-lock") }

	// Transform...
	region_transform_region { a.sendMsg("Region/transform-region") }

	// Transpose...
	region_transpose_region { a.sendMsg("Region/transpose-region") }

	// Trim End at Edit Point
	region_trim_back { a.sendMsg("Region/trim-back") }

	// Trim Start at Edit Point
	region_trim_front { a.sendMsg("Region/trim-front") }

	// Trim to Loop
	region_trim_region_to_loop { a.sendMsg("Region/trim-region-to-loop") }

	// Trim to Punch
	region_trim_region_to_punch { a.sendMsg("Region/trim-region-to-punch") }

	// Trim to Next
	region_trim_to_next_region { a.sendMsg("Region/trim-to-next-region") }

	// Trim to Previous
	region_trim_to_previous_region { a.sendMsg("Region/trim-to-previous-region") }

	// Uncombine
	region_uncombine_regions { a.sendMsg("Region/uncombine-regions") }

	// Sort
	regionlist_regionlistsort { a.sendMsg("RegionList/RegionListSort") }

	// Ascending
	regionlist_sortascending { a.sendMsg("RegionList/SortAscending") }

	// By Region End in File
	regionlist_sortbyregionendinfile { a.sendMsg("RegionList/SortByRegionEndinFile") }

	// By Region Length
	regionlist_sortbyregionlength { a.sendMsg("RegionList/SortByRegionLength") }

	// By Region Name
	regionlist_sortbyregionname { a.sendMsg("RegionList/SortByRegionName") }

	// By Region Position
	regionlist_sortbyregionposition { a.sendMsg("RegionList/SortByRegionPosition") }

	// By Region Start in File
	regionlist_sortbyregionstartinfile { a.sendMsg("RegionList/SortByRegionStartinFile") }

	// By Region Timestamp
	regionlist_sortbyregiontimestamp { a.sendMsg("RegionList/SortByRegionTimestamp") }

	// By Source File Creation Date
	regionlist_sortbysourcefilecreationdate { a.sendMsg("RegionList/SortBySourceFileCreationDate") }

	// By Source File Length
	regionlist_sortbysourcefilelength { a.sendMsg("RegionList/SortBySourceFileLength") }

	// By Source File Name
	regionlist_sortbysourcefilename { a.sendMsg("RegionList/SortBySourceFileName") }

	// By Source Filesystem
	regionlist_sortbysourcefilesystem { a.sendMsg("RegionList/SortBySourceFilesystem") }

	// Descending
	regionlist_sortdescending { a.sendMsg("RegionList/SortDescending") }

	// Remove Unused
	regionlist_removeunusedregions { a.sendMsg("RegionList/removeUnusedRegions") }

	// Audition
	regionlist_rlaudition { a.sendMsg("RegionList/rlAudition") }

	// Hide
	regionlist_rlhide { a.sendMsg("RegionList/rlHide") }

	// Show
	regionlist_rlshow { a.sendMsg("RegionList/rlShow") }

	// Show All
	regionlist_rlshowall { a.sendMsg("RegionList/rlShowAll") }

	// Show Automatic Regions
	regionlist_rlshowauto { a.sendMsg("RegionList/rlShowAuto") }

	// Bars & Beats
	rulers_toggle_bbt_ruler { a.sendMsg("Rulers/toggle-bbt-ruler") }

	// CD Markers
	rulers_toggle_cd_marker_ruler { a.sendMsg("Rulers/toggle-cd-marker-ruler") }

	// Loop/Punch
	rulers_toggle_loop_punch_ruler { a.sendMsg("Rulers/toggle-loop-punch-ruler") }

	// Markers
	rulers_toggle_marker_ruler { a.sendMsg("Rulers/toggle-marker-ruler") }

	// Meter
	rulers_toggle_meter_ruler { a.sendMsg("Rulers/toggle-meter-ruler") }

	// Min:Sec
	rulers_toggle_minsec_ruler { a.sendMsg("Rulers/toggle-minsec-ruler") }

	// Ranges
	rulers_toggle_range_ruler { a.sendMsg("Rulers/toggle-range-ruler") }

	// Samples
	rulers_toggle_samples_ruler { a.sendMsg("Rulers/toggle-samples-ruler") }

	// Tempo
	rulers_toggle_tempo_ruler { a.sendMsg("Rulers/toggle-tempo-ruler") }

	// Timecode
	rulers_toggle_timecode_ruler { a.sendMsg("Rulers/toggle-timecode-ruler") }

	// Video
	rulers_toggle_video_ruler { a.sendMsg("Rulers/toggle-video-ruler") }

	// Snap to Sixteenths
	snap_snap_to_asixteenthbeat { a.sendMsg("Snap/snap-to-asixteenthbeat") }

	// Snap to Bar
	snap_snap_to_bar { a.sendMsg("Snap/snap-to-bar") }

	// Snap to Beat
	snap_snap_to_beat { a.sendMsg("Snap/snap-to-beat") }

	// Snap to CD Frame
	snap_snap_to_cd_frame { a.sendMsg("Snap/snap-to-cd-frame") }

	// Snap to Eighths
	snap_snap_to_eighths { a.sendMsg("Snap/snap-to-eighths") }

	// Snap to Fifths
	snap_snap_to_fifths { a.sendMsg("Snap/snap-to-fifths") }

	// Snap to Fourteenths
	snap_snap_to_fourteenths { a.sendMsg("Snap/snap-to-fourteenths") }

	// Snap to Halves
	snap_snap_to_halves { a.sendMsg("Snap/snap-to-halves") }

	// Snap to Mark
	snap_snap_to_mark { a.sendMsg("Snap/snap-to-mark") }

	// Snap to Minutes
	snap_snap_to_minutes { a.sendMsg("Snap/snap-to-minutes") }

	// Snap to One Twenty Eighths
	snap_snap_to_onetwentyeighths { a.sendMsg("Snap/snap-to-onetwentyeighths") }

	// Snap to Quarters
	snap_snap_to_quarters { a.sendMsg("Snap/snap-to-quarters") }

	// Snap to Region Boundary
	snap_snap_to_region_boundary { a.sendMsg("Snap/snap-to-region-boundary") }

	// Snap to Region End
	snap_snap_to_region_end { a.sendMsg("Snap/snap-to-region-end") }

	// Snap to Region Start
	snap_snap_to_region_start { a.sendMsg("Snap/snap-to-region-start") }

	// Snap to Region Sync
	snap_snap_to_region_sync { a.sendMsg("Snap/snap-to-region-sync") }

	// Snap to Seconds
	snap_snap_to_seconds { a.sendMsg("Snap/snap-to-seconds") }

	// Snap to Sevenths
	snap_snap_to_sevenths { a.sendMsg("Snap/snap-to-sevenths") }

	// Snap to Sixths
	snap_snap_to_sixths { a.sendMsg("Snap/snap-to-sixths") }

	// Snap to Sixty Fourths
	snap_snap_to_sixtyfourths { a.sendMsg("Snap/snap-to-sixtyfourths") }

	// Snap to Tenths
	snap_snap_to_tenths { a.sendMsg("Snap/snap-to-tenths") }

	// Snap to Thirds
	snap_snap_to_thirds { a.sendMsg("Snap/snap-to-thirds") }

	// Snap to Thirty Seconds
	snap_snap_to_thirtyseconds { a.sendMsg("Snap/snap-to-thirtyseconds") }

	// Snap to Timecode Frame
	snap_snap_to_timecode_frame { a.sendMsg("Snap/snap-to-timecode-frame") }

	// Snap to Timecode Minutes
	snap_snap_to_timecode_minutes { a.sendMsg("Snap/snap-to-timecode-minutes") }

	// Snap to Timecode Seconds
	snap_snap_to_timecode_seconds { a.sendMsg("Snap/snap-to-timecode-seconds") }

	// Snap to Twelfths
	snap_snap_to_twelfths { a.sendMsg("Snap/snap-to-twelfths") }

	// Snap to Twentieths
	snap_snap_to_twentieths { a.sendMsg("Snap/snap-to-twentieths") }

	// Snap to Twenty Eighths
	snap_snap_to_twentyeighths { a.sendMsg("Snap/snap-to-twentyeighths") }

	// Snap to Twenty Fourths
	snap_snap_to_twentyfourths { a.sendMsg("Snap/snap-to-twentyfourths") }

	// Zoom Focus Center
	zoom_zoom_focus_center { a.sendMsg("Zoom/zoom-focus-center") }

	// Zoom Focus Edit Point
	zoom_zoom_focus_edit { a.sendMsg("Zoom/zoom-focus-edit") }

	// Zoom Focus Left
	zoom_zoom_focus_left { a.sendMsg("Zoom/zoom-focus-left") }

	// Zoom Focus Mouse
	zoom_zoom_focus_mouse { a.sendMsg("Zoom/zoom-focus-mouse") }

	// Zoom Focus Playhead
	zoom_zoom_focus_playhead { a.sendMsg("Zoom/zoom-focus-playhead") }

	// Zoom Focus Right
	zoom_zoom_focus_right { a.sendMsg("Zoom/zoom-focus-right") }

	// Toggle Selected Plugins
	mixer_ab_plugins { a.sendMsg("Mixer/ab-plugins") }

	// Copy Selected Processors
	mixer_copy_processors { a.sendMsg("Mixer/copy-processors") }

	// Cut Selected Processors
	mixer_cut_processors { a.sendMsg("Mixer/cut-processors") }

	// Increase Gain on Mixer-Selected Tracks/Busses
	mixer_decrement_gain { a.sendMsg("Mixer/decrement-gain") }

	// Delete Selected Processors
	mixer_delete_processors { a.sendMsg("Mixer/delete-processors") }

	// Decrease Gain on Mixer-Selected Tracks/Busses
	mixer_increment_gain { a.sendMsg("Mixer/increment-gain") }

	// Toggle Mute on Mixer-Selected Tracks/Busses
	mixer_mute { a.sendMsg("Mixer/mute") }

	// Paste Selected Processors
	mixer_paste_processors { a.sendMsg("Mixer/paste-processors") }

	// Toggle Rec-enable on Mixer-Selected Tracks/Busses
	mixer_recenable { a.sendMsg("Mixer/recenable") }

	// Scroll Mixer Window to the left
	mixer_scroll_left { a.sendMsg("Mixer/scroll-left") }

	// Scroll Mixer Window to the right
	mixer_scroll_right { a.sendMsg("Mixer/scroll-right") }

	// Select All (visible) Processors
	mixer_select_all_processors { a.sendMsg("Mixer/select-all-processors") }

	// Deselect all strips and processors
	mixer_select_none { a.sendMsg("Mixer/select-none") }

	// Toggle Solo on Mixer-Selected Tracks/Busses
	mixer_solo { a.sendMsg("Mixer/solo") }

	// Toggle MIDI Input Active for Editor-Selected Tracks/Busses
	mixer_toggle_midi_input_active { a.sendMsg("Mixer/toggle-midi-input-active") }

	// Toggle Selected Processors
	mixer_toggle_processors { a.sendMsg("Mixer/toggle-processors") }

	// Set Gain to 0dB on Mixer-Selected Tracks/Busses
	mixer_unity_gain { a.sendMsg("Mixer/unity-gain") }

}
