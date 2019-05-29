import {Component, Input, OnInit} from '@angular/core';
import {FreqChange} from '../../models/FreqChange';

@Component({
  selector: '[app-freq-change-item]',
  templateUrl: './freq-change-item.component.html',
  styleUrls: ['./freq-change-item.component.css']
})
export class FreqChangeItemComponent implements OnInit {

  @Input() freqChange: FreqChange;

  constructor() { }

  ngOnInit() {
  }

}
