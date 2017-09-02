package controller.spells;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.IO.Reader;
import model.world.Spell;

public class spellbookController {

	ArrayList<Spell> SpellList = new ArrayList<Spell>();

	@FXML
	private TextField fileNameField;

	@FXML
	private Label spellCount;

	@FXML
	private ListView<String> spellList;

	@FXML
	private TextArea spellNameField;

	@FXML
	private TextArea spellPreconditionField;

	@FXML
	private TextArea spellTextField;

	@FXML
	private TextArea spellDurationField;

	@FXML
	private TextArea spellCostField;

	@FXML
	void loadSpells(ActionEvent event) {
		SpellList = Reader.readSpells(fileNameField.getText());
		for (Spell s : SpellList) {
			spellList.getItems().add(s.getTitel());
		}
		spellCount.setText("" + SpellList.size());
	}

	@FXML
	void spellSelect(MouseEvent event) {

		// clear fields

		Spell selected = SpellList.get(spellList.getSelectionModel().getSelectedIndex());
		spellNameField.setText(selected.getTitel());
		spellPreconditionField.setText(selected.getPrecondition());
		spellTextField.setText(selected.getText());
		spellDurationField.setText(selected.getDuration());
		spellCostField.setText(selected.getCost());
	}

}
