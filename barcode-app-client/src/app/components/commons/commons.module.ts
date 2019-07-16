import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LabelWithInputComponent} from "./label-with-input/label-with-input.component";
import {LabelWithTextComponent} from "./label-with-text/label-with-text.component";
import {IconWithTextComponent} from "./icon-with-text/icon-with-text.component";
import {ButtonComponent} from "./button/button.component";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

@NgModule({
  declarations: [
    LabelWithInputComponent,
    LabelWithTextComponent,
    IconWithTextComponent,
    ButtonComponent,
  ],
  imports: [
    CommonModule,
    FontAwesomeModule
  ],
  exports:[
    LabelWithInputComponent,
    LabelWithTextComponent,
    IconWithTextComponent,
    ButtonComponent,
  ]
})
export class CommonsModule { }
