import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioHuecoComponent } from './formulario-hueco.component';

describe('FormularioHuecoComponent', () => {
  let component: FormularioHuecoComponent;
  let fixture: ComponentFixture<FormularioHuecoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioHuecoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FormularioHuecoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
