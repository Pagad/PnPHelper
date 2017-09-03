package controller.hero;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;
import model.Hero;
import model.Value;
import model.IO.Reader;
import model.calculator.Calculator;
import model.calculator.Term;
import model.world.Culture;
import model.world.CultureWithElement;
import model.world.Domain;
import model.world.Element;
import model.world.Gift;
import model.world.Handicap;
import model.world.Spell;
import view.hero.TermScene;
import view.spells.SpellBookScene;

public class HeroSceneController {

	@FXML
	private ChoiceBox<Culture> CultureList;

	@FXML
	private RadioButton cultureRadio;

	@FXML
	private ChoiceBox<Element> ElementList;

	@FXML
	private RadioButton elementRadio;

	@FXML
	private ChoiceBox<Integer> layerChoiceBox;

	@FXML
	private Label gPCount;

	@FXML
	private VBox baseValueList;

	@FXML
	private VBox NameColumn;

	@FXML
	private VBox MinColumn;

	@FXML
	private VBox MaxColumn;

	@FXML
	private VBox ValueColumn;

	@FXML
	private VBox BonusColumn;

	@FXML
	private VBox SumColumn;

	@FXML
	private Button startButton;

	@FXML
	private Button tButton;

	@FXML
	private TextArea testOutput;

	@FXML
	private ListView<Gift> selectedGifts;

	@FXML
	private ListView<Handicap> selectedHandicap;

	@FXML
	private ListView<Domain> selectedDomain;

	@FXML
	private TextArea infoArea;

	@FXML
	private ListView<Gift> allGifts;

	@FXML
	private ListView<Handicap> allHandicaps;

	@FXML
	private ListView<Domain> allDomains;

	private Hero h;
	private int gp = 500;

	@FXML
	void initialize() {

		loadStuff();

		h = new Hero();
		gPCount.setText("" + gp);

		ObservableList<String> culturelist = FXCollections.observableArrayList();
		for (Culture c : Culture.allCultures) {
			culturelist.add(c.getName());

		}

		ObservableList<Integer> layerlist = FXCollections.observableArrayList();
		for (int i = 1; i < 8; i++) {
			layerlist.add(i);
		}
		layerChoiceBox.setItems(layerlist);
		layerChoiceBox.getSelectionModel().select(0);
		layerChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				updateGPCount();

			}

		});

		for (Value v : Hero.baseValueList) {
			Label nameLabel = new Label(v.getName());
			nameLabel.setFont(new Font(16));
			nameLabel.setTextAlignment(TextAlignment.CENTER);
			NameColumn.getChildren().add(nameLabel);

			Label minLabel = new Label("0");
			minLabel.setFont(new Font(16));
			minLabel.setTextAlignment(TextAlignment.CENTER);
			MinColumn.getChildren().add(minLabel);

			TextField valueField = new TextField("10");
			valueField.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					valueChange();
				}

			});
			ValueColumn.getChildren().add(valueField);

			Label maxLabel = new Label("0");
			maxLabel.setFont(new Font(16));
			maxLabel.setTextAlignment(TextAlignment.CENTER);
			MaxColumn.getChildren().add(maxLabel);

			Label bonusLabel = new Label("0");
			bonusLabel.setFont(new Font(16));
			bonusLabel.setTextAlignment(TextAlignment.CENTER);
			BonusColumn.getChildren().add(bonusLabel);
		}

		for (int i = 2; i < NameColumn.getChildren().size(); i++) {
			int sum = Integer.parseInt(((TextField) ValueColumn.getChildren().get(i)).getText());
			Label sumLabel = new Label("" + sum);
			sumLabel.setFont(new Font(16));
			sumLabel.setTextAlignment(TextAlignment.CENTER);
			SumColumn.getChildren().add(sumLabel);
		}

		ToggleGroup tg = new ToggleGroup();
		cultureRadio.setToggleGroup(tg);
		cultureRadio.setSelected(true);
		elementRadio.setToggleGroup(tg);

		CultureList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Culture>() {
			@Override
			public void changed(ObservableValue<? extends Culture> observable, Culture oldValue, Culture newValue) {
				if (cultureRadio.isSelected()) {
					ElementList.getItems().clear();
					Culture c = CultureList.getSelectionModel().getSelectedItem();
					ElementList.getItems().addAll(c.getElements());
				}
				updateMinMaxBonusValues();
				updateGPCount();
				updateSumValue();
			}
		});

		ElementList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Element>() {
			@Override
			public void changed(ObservableValue<? extends Element> observable, Element oldValue, Element newValue) {
				if (elementRadio.isSelected()) {
					Element e = ElementList.getSelectionModel().getSelectedItem();
					CultureList.getItems().clear();
						for (Culture c : Culture.allCultures) {
							if (c.getElements().contains(e)) {
								CultureList.getItems().add(c);
							}
						}
					}
				updateMinMaxBonusValues();
				updateGPCount();
				updateSumValue();
			}
		});

		allGifts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gift>() {
			@Override
			public void changed(ObservableValue<? extends Gift> arg0, Gift arg1, Gift arg2) {
				if (arg2 != null)
					infoArea.setText(arg2.getText());
			}

		});

		selectedGifts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gift>() {
			@Override
			public void changed(ObservableValue<? extends Gift> arg0, Gift arg1, Gift arg2) {
				if (arg2 != null)
					infoArea.setText(arg2.getText());
			}

		});

		allHandicaps.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Handicap>() {
			@Override
			public void changed(ObservableValue<? extends Handicap> arg0, Handicap arg1, Handicap arg2) {
				if (arg2 != null)
					infoArea.setText(arg2.getText());
			}

		});

		selectedHandicap.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Handicap>() {
			@Override
			public void changed(ObservableValue<? extends Handicap> arg0, Handicap arg1, Handicap arg2) {
				if (arg2 != null)
					infoArea.setText(arg2.getText());
			}

		});

		allDomains.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Domain>() {
			@Override
			public void changed(ObservableValue<? extends Domain> arg0, Domain arg1, Domain arg2) {
				if (arg2 != null)
					infoArea.setText(arg2.getText());
			}

		});

		selectedDomain.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Domain>() {
			@Override
			public void changed(ObservableValue<? extends Domain> arg0, Domain arg1, Domain arg2) {
				if (arg2 != null)
					infoArea.setText(arg2.getText());

			}

		});

		updateGPCount();
	}

	private void loadStuff() {

		CultureList.getItems().addAll(Culture.allCultures);

		ElementList.getItems().addAll(Element.allElements);

		for (Gift g : Gift.allGifts) {
			allGifts.getItems().add(g);
		}

		for (Handicap h : Handicap.allHandicaps) {
			allHandicaps.getItems().add(h);
		}

		for (Domain d : Domain.allDomains) {
			allDomains.getItems().add(d);
		}

	}

	protected void valueChange() {
		updateGPCount();
		updateSumValue();
		updateMinMaxBonusValues();

	}

	private void updateSumValue() {

		while (SumColumn.getChildren().size() > 2) { // clearLists
			SumColumn.getChildren().remove(2);
		}

		for (int i = 2; i < NameColumn.getChildren().size(); i++) {
			int sum = Integer.parseInt(((TextField) (ValueColumn.getChildren().get(i))).getText());
			sum += Integer.parseInt(((Label) (BonusColumn.getChildren().get(i))).getText());
			Label label = new Label("" + sum);
			label.setFont(new Font(16));
			label.setTextAlignment(TextAlignment.CENTER);
			SumColumn.setAlignment(Pos.CENTER);
			SumColumn.getChildren().add(label);
		}

	}

	private void updateGPCount() {
		int sum = 0;
		for (int i = 2; i < ValueColumn.getChildren().size(); i++) {
			String s = ((TextField) (ValueColumn.getChildren().get(i))).getText();
			sum += Integer.parseInt(s);
		}
		sum += (layerChoiceBox.getValue() - 1) * 10;

		gp = Hero.START_GP - sum;
		gPCount.setText("" + gp);
		if (gp < 0) {
			gPCount.setTextFill(Color.RED);
		} else {
			gPCount.setTextFill(Color.BLACK);
		}
	}

	@FXML
	void cultureRadioChoose(ActionEvent event) {
		CultureList.getItems().addAll(Culture.allCultures);
		Element e = ElementList.getSelectionModel().getSelectedItem();
		ElementList.getItems().clear();
		ElementList.getItems().addAll(CultureList.getSelectionModel().getSelectedItem().getElements());
		ElementList.getSelectionModel().select(e);
	}

	@FXML
	void elementRadioChoose(ActionEvent event) {
		ElementList.getItems().addAll(Element.allElements);
		Culture cs =CultureList.getSelectionModel().getSelectedItem();
		CultureList.getItems().clear();
		for (Culture c : Culture.allCultures) {
			if (c.getElements().contains(ElementList.getSelectionModel().getSelectedItem())) {
				CultureList.getItems().add(c);
			}
		}
		CultureList.getSelectionModel().select(cs);
	}

	private void updateMinMaxBonusValues() {
		if (CultureList.getSelectionModel().getSelectedItem() != null
				&& ElementList.getSelectionModel().getSelectedItem() != null) {

			Culture c = CultureList.getSelectionModel().getSelectedItem();
			Element e = ElementList.getSelectionModel().getSelectedItem();

			CultureWithElement cWe = CultureWithElement.getCultureWithElement(c, e);

			while (MinColumn.getChildren().size() > 2) { // clearLists
				MinColumn.getChildren().remove(2);
				MaxColumn.getChildren().remove(2);
				BonusColumn.getChildren().remove(2);
			}

			for (int i = 2; i < NameColumn.getChildren().size(); i++) { // setValues
																		// from
																		// cWe

				int value = Integer.parseInt(((TextField) (ValueColumn.getChildren().get(i))).getText());

				int min = cWe.getMinValues().get(i - 2).getNumber();
				Label minLabel = new Label(min + "");
				minLabel.setFont(new Font(16));
				minLabel.setTextAlignment(TextAlignment.RIGHT);
				MinColumn.setAlignment(Pos.CENTER);
				MinColumn.getChildren().add(minLabel);

				if (value < min) {
					minLabel.setTextFill(Color.RED);
				}

				int max = cWe.getMaxValues().get(i - 2).getNumber();
				Label maxLabel = new Label(max + "");
				maxLabel.setFont(new Font(16));
				maxLabel.setTextAlignment(TextAlignment.RIGHT);
				MaxColumn.setAlignment(Pos.CENTER);
				MaxColumn.getChildren().add(maxLabel);

				if (value > max) {
					maxLabel.setTextFill(Color.RED);
				}

				Label bonusLabel = new Label(cWe.getBonusValues().get(i - 2).getNumber() + "");
				bonusLabel.setFont(new Font(16));
				bonusLabel.setTextAlignment(TextAlignment.CENTER);
				BonusColumn.setAlignment(Pos.CENTER);
				BonusColumn.getChildren().add(bonusLabel);

			}
		}

	}

	@FXML
	void selectDomain(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Domain d = allDomains.getSelectionModel().getSelectedItem();
			selectedDomain.getItems().add(d);
			allDomains.getItems().remove(d);
		}
	}

	@FXML
	void selectGift(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Gift g = allGifts.getSelectionModel().getSelectedItem();
			selectedGifts.getItems().add(g);
			allGifts.getItems().remove(g);
		}
	}

	@FXML
	void selectHandicap(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Handicap h = allHandicaps.getSelectionModel().getSelectedItem();
			selectedHandicap.getItems().add(h);
			allHandicaps.getItems().remove(h);
		}
	}

	@FXML
	void deselectDomain(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Domain d = selectedDomain.getSelectionModel().getSelectedItem();
			allDomains.getItems().add(d);
			selectedDomain.getItems().remove(d);
		}
	}

	@FXML
	void deselectGift(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Gift g = selectedGifts.getSelectionModel().getSelectedItem();
			allGifts.getItems().add(g);
			selectedGifts.getItems().remove(g);
		}
	}

	@FXML
	void deselectHandicap(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Handicap h = selectedHandicap.getSelectionModel().getSelectedItem();
			allHandicaps.getItems().add(h);
			selectedHandicap.getItems().remove(h);
		}
	}

	@FXML
	void startTest(ActionEvent event) {
		testOutput.setText("teset1");
		for (Value v : Main.hero.getValues()) {
			testOutput.setText(testOutput.getText() + "\n" + v.getName() + ": " + v.getNumber() + "=> "
					+ Calculator.calc(Main.hero, v.getTerm()));
		}
		Main.hero.calculateValues();
	}

	@FXML
	void goToTermScene(ActionEvent event) {
		Main.primStage.setScene(SpellBookScene.spellBookScene);
	}

	@FXML
	void startValues(ActionEvent event) {

	}

}
