/*
	Class to control Ardour from inside SuperCollider
	
	Use:

	a = Ardour();
	b = BCFSlider();
	b.removeAll;
	8.do({|i| i= i+1;
	b.spec(i, [0.0, 2.0, \lin, 0.00000001, 1, "abs"]);
	b.func(i, {|v| a.abs(i, v)})
	});
	//

	a = Ardour()
	a.mute(1, 1) // channel 1 mute ON
	a.solo(1, 1) // channel 1, solo ON
	a.solo(1, 0) // solo OFF
	a.rec(1, 1) // channel 1, rec ON
	a.rec(1, 0) // channel 1, rec OFF
	a.rec(2, 1) // channel 2, rec ON
	a.rec(2, 0) // channel 2, rec OFF
	
	a.abs(1, 1)

	See list bellow for more actions
*/


Ardour {
	
	var ardourOsc;
	
	*new { 

		^super.new.init;
	}

	init { 
		
		ardourOsc = NetAddr("127.0.0.1", 3819);
	}

	bigClock {

		ardourOsc.sendMsg("/ardour/access_action", "Common/ToggleBigClock")
	}
	
	cmd { arg newMsg; // general OSC message, see list bellow

		ardourOsc.sendMsg("/ardour/access_action", newMsg)
	}
	
	nextMarker {

		ardourOsc.sendMsg("/ardour/next_marker")
	}

	prevMarker {

		ardourOsc.sendMsg("/ardour/prev_marker")
	}
	
	
	mute { arg channel, onOff;
		
		ardourOsc.sendMsg("/ardour/routes/mute", channel, onOff);		
	}
	
	abs { arg channel, vol; // 0.0 -> 2.0 // 1.0 = 0db

		ardourOsc.sendMsg("/ardour/routes/gainabs", channel, vol);
	}
	
	gotoStart {

		ardourOsc.sendMsg("/ardour/goto_start");
	}

	addMarker {

		ardourOsc.sendMsg("/ardour/add_marker");
	}
	
	save {

		ardourOsc.sendMsg("/ardour/save_state");		
	}

	dB { arg channel, thisDB;

		ardourOsc.sendMsg("/ardour/routes/gaindB", channel, thisDB);		
	}
	
	recToggle {

		ardourOsc.sendMsg("/ardour/rec_enable_toggle");	
	}

	play {

		ardourOsc.sendMsg("/ardour/transport_play");	
	}
	
	solo { arg channel, onOff;
		
		ardourOsc.sendMsg("/ardour/routes/solo", channel, onOff);		
	}
	
	rec { arg channel, onOff;

		ardourOsc.sendMsg("/ardour/routes/recenable", channel, onOff);	
	}

	stop {

		ardourOsc.sendMsg("/ardour/transport_stop");
	}

	rwd {

		ardourOsc.sendMsg("/ardour/rewind");		
	}

	fwd {

		ardourOsc.sendMsg("/ardour/ffwd");	
	}

	// map BCF2000 faders to Ardour
	mapBCF {

		var bcf;
		bcf = BCFSlider();
		bcf.removeAll;
		8.do({|i| i= i+1;
			bcf.spec(i, [0.0, 2.0, \lin, 0.00000001, 1, "abs"]);
			bcf.func(i, {|v| this.abs(i, v)})
		});		
	}
	
}


/*
Common/About
Common/goto-editor
Common/Quit
Common/Save
Common/toggle-editor-mixer-on-top
Common/ToggleBigClock
Common/ToggleColorManager
Common/ToggleInspector
Common/ToggleKeyEditor
Common/ToggleLocations
Common/ToggleMaximalEditor
Common/ToggleOptionsEditor
Editor/add-location-from-playhead
Editor/addExistingAudioFiles
Editor/align-regions-end
Editor/align-regions-start
Editor/align-regions-start-relative
Editor/align-regions-sync
Editor/align-regions-sync-relative
Editor/audition-at-mouse
Editor/Autoconnect
Editor/boost-region-gain
Editor/brush-at-mouse
Editor/center-edit-cursor
Editor/center-playhead
Editor/crop
Editor/Crossfades
Editor/CrossfadesFull
Editor/CrossfadesShort
Editor/cut-region-gain
Editor/cycle-edit-point
Editor/cycle-edit-point-with-marker
Editor/cycle-snap-choice
Editor/cycle-snap-mode
Editor/duplicate-region
Editor/Edit
Editor/edit-cursor-to-next-region-sync
Editor/edit-cursor-to-previous-region-start
Editor/edit-cursor-to-previous-region-sync
Editor/edit-cursor-to-range-end
Editor/edit-cursor-to-range-start
Editor/edit-to-playhead
Editor/EditCursorMovementOptions
Editor/editor-copy
Editor/editor-cut
Editor/editor-delete
Editor/editor-paste
Editor/EditSelectRangeOptions
Editor/EditSelectRegionOptions
Editor/export-region
Editor/extend-range-to-end-of-region
Editor/extend-range-to-start-of-region
Editor/finish-add-range
Editor/finish-range
Editor/fit-tracks
Editor/goto-mark-1
Editor/goto-mark-2
Editor/goto-mark-3
Editor/goto-mark-4
Editor/goto-mark-5
Editor/goto-mark-6
Editor/goto-mark-7
Editor/goto-mark-8
Editor/goto-mark-9
Editor/goto-visual-state-1
Editor/goto-visual-state-2
Editor/goto-visual-state-3
Editor/goto-visual-state-4
Editor/goto-visual-state-5
Editor/goto-visual-state-6
Editor/goto-visual-state-7
Editor/goto-visual-state-8
Editor/goto-visual-state-9
Editor/goto-visual-state-10
Editor/goto-visual-state-11
Editor/goto-visual-state-12
Editor/insert-region
Editor/insert-time
Editor/invert-selection
Editor/jump-backward-to-mark
Editor/jump-forward-to-mark
Editor/LayerAddHigher
Editor/Layering
Editor/LayerLaterHigher
Editor/LayerMoveAddHigher
Editor/MeterFalloff
Editor/MeterHold
Editor/Monitoring
Editor/move-selected-tracks-down
Editor/move-selected-tracks-up
Editor/multi-duplicate-region
Editor/mute-unmute-region
Editor/naturalize-region
Editor/normalize-region
Editor/nudge-backward
Editor/nudge-forward
Editor/nudge-next-backward
Editor/nudge-next-forward
Editor/nudge-playhead-backward
Editor/nudge-playhead-forward
Editor/pitch-[Shift]-region
Editor/pitch-shift-region
Editor/play-edit-range
Editor/play-from-edit-point-and-return
Editor/play-selected-regions
Editor/playhead-backward-to-grid
Editor/playhead-forward-to-grid
Editor/playhead-to-edit
Editor/playhead-to-next-region-boundary
Editor/playhead-to-next-region-boundary-noselection
Editor/playhead-to-next-region-sync
Editor/playhead-to-next-region-sync
Editor/playhead-to-previous-region-boundary
Editor/playhead-to-previous-region-boundary-noselection
Editor/playhead-to-previous-region-sync
Editor/playhead-to-range-end
Editor/playhead-to-range-start
Editor/PullupMinus1
Editor/PullupMinus4
Editor/PullupMinus4Minus1
Editor/PullupMinus4Plus1
Editor/PullupNone
Editor/PullupPlus1
Editor/PullupPlus4
Editor/PullupPlus4Minus1
Editor/PullupPlus4Plus1
Editor/redo
Editor/RegionEditOps
Editor/remove-last-capture
Editor/reverse-region
Editor/save-visual-state-1
Editor/save-visual-state-2
Editor/save-visual-state-3
Editor/save-visual-state-4
Editor/save-visual-state-5
Editor/save-visual-state-6
Editor/save-visual-state-7
Editor/save-visual-state-8
Editor/save-visual-state-9
Editor/save-visual-state-10
Editor/save-visual-state-11
Editor/save-visual-state-12
Editor/scroll-tracks-down
Editor/scroll-tracks-up
Editor/select-all
Editor/select-all-after-edit-cursor
Editor/select-all-after-playhead
Editor/select-all-before-edit-cursor
Editor/select-all-before-playhead
Editor/select-all-between-cursors
Editor/select-all-in-loop-range
Editor/select-all-in-punch-range
Editor/select-all-within-cursors
Editor/select-next-route
Editor/select-prev-route
Editor/select-range-between-cursors
Editor/selected-marker-to-next-region-boundary
Editor/selected-marker-to-previous-region-boundary
Editor/separate
Editor/set-edit-point
Editor/set-fade-in-length
Editor/set-fade-out-length
Editor/set-loop-from-edit-range
Editor/set-loop-from-region
Editor/set-playhead
Editor/set-punch-from-edit-range
Editor/set-punch-from-region
Editor/set-region-sync-position
Editor/set-tempo-from-edit-range
Editor/set-tempo-from-region
Editor/show-editor-mixer
Editor/Smpte24
Editor/Smpte25
Editor/Smpte30
Editor/Smpte30drop
Editor/Smpte60
Editor/Smpte2997
Editor/Smpte5994
Editor/Smpte2997drop
Editor/Smpte23976
Editor/Smpte24976
Editor/snap-magnetic
Editor/snap-normal
Editor/SnapMode
Editor/SnapTo
Editor/Solo
Editor/split-region
Editor/start-range
Editor/step-tracks-down
Editor/step-tracks-up
Editor/Subframes
Editor/Subframes100
Editor/Subframes80
Editor/tab-to-transient-backwards
Editor/tab-to-transient-forwards
Editor/temporal-zoom-in
Editor/temporal-zoom-out
Editor/Timecode
Editor/toggle-auto-xfades
Editor/toggle-edit-mode
Editor/toggle-fade-out-active
Editor/toggle-fade-out-active
Editor/toggle-follow-playhead
Editor/toggle-internal-edit
Editor/toggle-rhythm-ferret
Editor/toggle-xfades-active
Editor/toggle-xfades-visible
Editor/toggle-zoom
Editor/ToggleGeneric MIDISurface
Editor/ToggleGeneric MIDISurfaceFeedback
Editor/ToggleGeneric MIDISurfaceSubMenu
Editor/ToggleMeasureVisibility
Editor/ToggleWaveformsWhileRecording
Editor/ToggleWaveformVisibility
Editor/track-record-enable-toggle
Editor/trim-back
Editor/trim-from-start
Editor/trim-front
Editor/trim-to-end
Editor/undo
Editor/View
Editor/zoom-to-region
Editor/zoom-to-region-both-axes
Editor/zoom-to-session
Editor/ZoomFocus
JACK/JACK
JACK/JACKDisconnect
JACK/JACKReconnect
JACK/JACKLatency32
JACK/JACKLatency64
JACK/JACKLatency128
JACK/JACKLatency256
JACK/JACKLatency512
JACK/JACKLatency1024
JACK/JACKLatency2048
JACK/JACKLatency4096
JACK/JACKLatency8192
Main/AddTrackBus
Main/AudioFileFormat
Main/AudioFileFormatData
Main/AudioFileFormatHeader
Main/CleanupUnused
Main/FlushWastebasket
Main/Close
Main/ControlSurfaces
Main/Export
Main/ExportRangeMarkers
Main/ExportSelection
Main/ExportSession
Main/Help
Main/KeyMouse Actions
Main/Metering
Main/MeteringFallOffRate
Main/MeteringHoldTime
Main/New
Main/Open
Main/Options
Main/Recent
Main/SaveTemplate
Main/Session
Main/Snapshot
Main/TransportOptions
Main/Windows
MouseMode/set-mouse-mode-gain
MouseMode/set-mouse-mode-object
MouseMode/set-mouse-mode-range
MouseMode/set-mouse-mode-timefx
MouseMode/set-mouse-mode-zoom
options/DoNotRunPluginsWhileRecording
options/FileDataFormat24bit
options/FileDataFormatFloat
options/FileHeaderFormatBWF
options/FileHeaderFormatCAF
options/FileHeaderFormatWAVE
options/FileHeaderFormatWAVE64
options/GainReduceFastTransport
options/InputAutoConnectManual
options/InputAutoConnectPhysical
options/LatchedRecordEnable
options/LatchedSolo
options/MeterFalloffFast
options/MeterFalloffFaster
options/MeterFalloffFastest
options/MeterFalloffMedium
options/MeterFalloffOff
options/MeterFalloffSlow
options/MeterFalloffSlowest
options/MeterHoldLong
options/MeterHoldMedium
options/MeterHoldOff
options/MeterHoldShort
options/OutputAutoConnectManual
options/OutputAutoConnectMaster
options/OutputAutoConnectPhysical
options/RegionEquivalentsOverlap
options/SendMMC
options/SendMTC
options/ShowSoloMutes
options/SoloInPlace
options/SoloViaBus
options/StopPluginsWithTransport
options/StopRecordingOnXrun
options/StopTransportAtEndOfSession
options/UseExternalMonitoring
options/UseHardwareMonitoring
options/UseMMC
options/UseSoftwareMonitoring
options/VerifyRemoveLastCapture
redirectmenu/activate
redirectmenu/activate_all
redirectmenu/clear
redirectmenu/copy
redirectmenu/cut
redirectmenu/deactivate
redirectmenu/deactivate_all
redirectmenu/deselectall
redirectmenu/edit
redirectmenu/newinsert
redirectmenu/newplugin
redirectmenu/newsend
redirectmenu/paste
redirectmenu/rename
redirectmenu/selectall
RegionList/RegionListSort
RegionList/RegionListSort
RegionList/rlAudition
RegionList/rlHide
RegionList/rlRemove
RegionList/rlShowAll
RegionList/rlShowAuto
RegionList/SortAscending
RegionList/SortByRegionEndinFile
RegionList/SortByRegionLength
RegionList/SortByRegionName
RegionList/SortByRegionPosition
RegionList/SortByRegionStartinFile
RegionList/SortByRegionTimestamp
RegionList/SortBySourceFileCreationDate
RegionList/SortBySourceFileLength
RegionList/SortBySourceFileName
RegionList/SortBySourceFilesystem
RegionList/SortDescending
ShuttleActions/SetShuttleUnitsPercentage
ShuttleActions/SetShuttleUnitsSemitones
Snap/snap-to-asixteenthbeat
Snap/snap-to-bar
Snap/snap-to-beat
Snap/snap-to-cd-frame
Snap/snap-to-edit-cursor
Snap/snap-to-eighths
Snap/snap-to-frame
Snap/snap-to-mark
Snap/snap-to-minutes
Snap/snap-to-quarters
Snap/snap-to-region-boundary
Snap/snap-to-region-end
Snap/snap-to-region-start
Snap/snap-to-region-sync
Snap/snap-to-seconds
Snap/snap-to-smpte-frame
Snap/snap-to-smpte-minutes
Snap/snap-to-smpte-seconds
Snap/snap-to-thirds
Snap/snap-to-thirtyseconds
Transport/focus-on-clock
Transport/Forward
Transport/GotoEnd
Transport/GotoStart
Transport/GotoZero
Transport/Loop
Transport/PlaySelection
Transport/Record
Transport/record-roll
Transport/Rewind
Transport/ToggleAutoInput
Transport/ToggleAutoPlay
Transport/ToggleAutoReturn
Transport/ToggleClick
Transport/TogglePunchIn
Transport/TogglePunchOut
Transport/ToggleRoll
Transport/ToggleRollForgetCapture
Transport/ToggleRollMaybe
Transport/ToggleTimeMaster
Transport/ToggleVideoSync
Transport/TransitionToReverse
Transport/TransitionToRoll
Zoom/zoom-focus-center
Zoom/zoom-focus-edit
Zoom/zoom-focus-left
Zoom/zoom-focus-playhead
Zoom/zoom-focus-right
*/