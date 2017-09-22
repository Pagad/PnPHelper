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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;
import model.Hero;
import model.LvLUp;
import model.Player;
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
import view.hero.HeroOverviewScene;
import view.hero.TermScene;
import view.spells.SpellBookScene;

public class HeroSceneController {

    @FXML
    private ComboBox<Player> playerName;

	@FXML
	private TextField heroName;

	@FXML
	private TextField addEpField;

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
	private TextField EpField;

	@FXML
	private Button lvlUpButton;

	@FXML
	private Label LvlCount;

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
	private HBox LPBox;

	@FXML
	private HBox MPBox;

	@FXML
	private VBox MagieBox;

	@FXML
	private VBox NahkampfBox;

	@FXML
	private VBox AllgemeineBox;

	@FXML
	private ListView<Gift> selectedGifts;

	@FXML
	private Label giftCostLabel;

	@FXML
	private ListView<Handicap> selectedHandicap;

	@FXML
	private Label handicapCostLabel;

	@FXML
	private ListView<Domain> selectedDomain;

	@FXML
	private Label domainCostLabel;

	@FXML
	private TextArea infoArea;

	@FXML
	private ListView<Gift> allGifts;

	@FXML
	private ListView<Handicap> allHandicaps;

	@FXML
	private ListView<Domain> allDomains;

	@FXML
	private ListView<Spell> spellListElement;

	@FXML
	private ListView<Spell> spellListAllg;

	private Hero hero;
	private int gp = 500;

	private int domainCost = 0;

	private int giftCost = 0;

	private int handicapCost = 0;

	private ArrayList<Value> levelUp = new ArrayList<Value>();

	@FXML
	void initialize() {
		reset();

		loadStuff();

		playerName.getItems().addAll(Player.PlayerList);
		
		lvlUpButton.setDisable(false);
		gPCount.setText("" + gp);

		for (Value v : Hero.baseValueList) {
			Label nameLabel = new Label(v.getName());
			nameLabel.setFont(new Font(16));
			nameLabel.setTextAlignment(TextAlignment.CENTER);
			NameColumn.setAlignment(Pos.CENTER);
			NameColumn.getChildren().add(nameLabel);

			Label minLabel = new Label("0");
			minLabel.setFont(new Font(16));
			minLabel.setTextAlignment(TextAlignment.CENTER);
			MinColumn.setAlignment(Pos.CENTER);
			MinColumn.getChildren().add(minLabel);

			HBox valueField = new HBox();
			{
				TextField valueTextField = new TextField("0");

				valueTextField.textProperty().addListener(y -> {
					try {
						if (hero.getLvL() == 0) {
							int numb = Integer.parseInt(valueTextField.getText());
							hero.addValue(new Value(v.getName(), numb));
						}
					} catch (NumberFormatException e) {
					}
					allUpdates();
				});

				Button minusButton = new Button("-");
				minusButton.setFont(new Font(10));
				minusButton.setMaxWidth(10);
				minusButton.setOnAction(y -> {
					try {
						if (Integer.parseInt(valueTextField.getText()) > hero.getMinValueByName(v.getName())
								.getNumber()) {
							if (hero.getLvL() == 0) {
								hero.addValue(new Value(v.getName(), Integer.parseInt(valueTextField.getText())));
							} else { // lvl!=0
								levelUp.add(new Value(v.getName(), -1));
							}
							valueTextField.setText(Integer.parseInt((valueTextField.getText())) - 1 + "");
							allUpdates();
						}
					} catch (NumberFormatException e) {
					}
				});

				valueField.getChildren().add(minusButton);

				valueField.getChildren().add(valueTextField);

				Button plusButton = new Button("+");
				plusButton.setFont(new Font(10));
				plusButton.setMaxWidth(10);
				plusButton.setOnAction(y -> {
					try {
						if (hero.getLvL() == 0) {
							hero.addValue(new Value(v.getName(), Integer.parseInt(valueTextField.getText())));
						} else { // lvl!=0
							levelUp.add(new Value(v.getName(), 1));
						}
						valueTextField.setText(Integer.parseInt((valueTextField.getText())) + 1 + "");
						allUpdates();
					} catch (NumberFormatException e) {
					}
				});
				valueField.getChildren().add(plusButton);
			}
			ValueColumn.setAlignment(Pos.CENTER);
			ValueColumn.getChildren().add(valueField);

			Label maxLabel = new Label("0");
			maxLabel.setFont(new Font(16));
			maxLabel.setTextAlignment(TextAlignment.CENTER);
			MaxColumn.setAlignment(Pos.CENTER);
			MaxColumn.getChildren().add(maxLabel);

			Label bonusLabel = new Label("0");
			bonusLabel.setFont(new Font(16));
			bonusLabel.setTextAlignment(TextAlignment.CENTER);
			BonusColumn.setAlignment(Pos.CENTER);
			BonusColumn.getChildren().add(bonusLabel);
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
					if (c != null)
						ElementList.getItems().addAll(c.getElements());
				}
				allUpdates();
			}
		});

		ElementList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Element>() {
			@Override
			public void changed(ObservableValue<? extends Element> observable, Element oldValue, Element newValue) {
				Element e = ElementList.getSelectionModel().getSelectedItem();
				if (elementRadio.isSelected()) {
					CultureList.getItems().clear();
					for (Culture c : Culture.allCultures) {
						if (c.getElements().contains(e)) {
							CultureList.getItems().add(c);
						}
					}
				}
				spellListElement.getItems().clear();
				if (e != null) {
					spellListElement.getItems().addAll(e.getSpells());
				}
				allUpdates();
			}
		});

		// player and hero name + Layer Lambda expressions

		playerName.getSelectionModel().selectedItemProperty().addListener((arg, oldVal, newVal) -> {
			hero.setMyPlayer(newVal);			
		});

		heroName.textProperty().addListener(y -> {
			hero.setName(heroName.getText());
		});

		layerChoiceBox.getSelectionModel().selectedItemProperty().addListener((arg, oldVal, newVal) -> {
			if (newVal != null) {
				hero.setLayer(newVal.intValue());
				updateGPCount();
			}
		});

		// set infoArea Lambda expression

		spellListElement.getSelectionModel().selectedItemProperty()
				.addListener((arg, oldVal, newVal) -> infoArea.setText(newVal == null ? "" : newVal.getText()));

		spellListAllg.getSelectionModel().selectedItemProperty()
				.addListener((arg, oldVal, newVal) -> infoArea.setText(newVal == null ? "" : newVal.getText()));

		allGifts.getSelectionModel().selectedItemProperty()
				.addListener((arg, oldVal, newVal) -> infoArea.setText(newVal == null ? "" : newVal.getText()));

		selectedGifts.getSelectionModel().selectedItemProperty()
				.addListener((arg, oldVal, newVal) -> infoArea.setText(newVal == null ? "" : newVal.getText()));

		allHandicaps.getSelectionModel().selectedItemProperty()
				.addListener((arg, oldVal, newVal) -> infoArea.setText(newVal == null ? "" : newVal.getText()));

		selectedHandicap.getSelectionModel().selectedItemProperty()
				.addListener((arg, oldVal, newVal) -> infoArea.setText(newVal == null ? "" : newVal.getText()));

		allDomains.getSelectionModel().selectedItemProperty()
				.addListener((arg, oldVal, newVal) -> infoArea.setText(newVal == null ? "" : newVal.getText()));

		selectedDomain.getSelectionModel().selectedItemProperty()
				.addListener((arg, oldVal, newVal) -> infoArea.setText(newVal == null ? "" : newVal.getText()));

		// allUpdates();
	}

	private void reset() {

		spellListAllg.getItems().clear();
		spellListAllg.getItems().addAll(Element.getElementFromName("Allg").getSpells());

		playerName.getSelectionModel().clearSelection();
		heroName.setText("");

		infoArea.setText("");

		for (int i = 2; i < NameColumn.getChildren().size(); i++) {
			HBox hh = (HBox) (ValueColumn.getChildren().get(i));
			TextField h = (TextField) (hh.getChildren().get(1));
			h.setText("0");
		}

		/*
		 * for (int i = 2; i < NameColumn.getChildren().size(); i++) { HBox hh = (HBox)
		 * (ValueColumn.getChildren().get(i)); TextField h = (TextField)
		 * (hh.getChildren().get(1)); int sum = Integer.parseInt(h.getText()); Label
		 * sumLabel = new Label("" + sum); sumLabel.setFont(new Font(16));
		 * sumLabel.setTextAlignment(TextAlignment.CENTER);
		 * SumColumn.getChildren().add(sumLabel); }
		 */
	}

	private void loadStuff() {

		layerChoiceBox.getItems().clear();
		for (int i = 1; i < 8; i++) {
			layerChoiceBox.getItems().add(i);
		}

		CultureList.getItems().clear();
		CultureList.getItems().addAll(Culture.allCultures);

		ElementList.getItems().clear();
		ElementList.getItems().addAll(Element.allElements);

		allGifts.getItems().clear();
		allGifts.getItems().addAll(Gift.allGifts);

		allHandicaps.getItems().clear();
		allHandicaps.getItems().addAll(Handicap.allHandicaps);

		allDomains.getItems().clear();
		allDomains.getItems().addAll(Domain.allDomains);

	}

	protected void allUpdates() {
		if (CultureList.getSelectionModel().getSelectedItem() != null
				&& ElementList.getSelectionModel().getSelectedItem() != null) {
			hero.setCwE(CultureWithElement.getCultureWithElement(CultureList.getSelectionModel().getSelectedItem(),
					ElementList.getSelectionModel().getSelectedItem()));
			updateMinMaxBonusValues();
			updateGPCount();
			updateSumValue();
			updateFightValues();
		}
	}

	private void updateBaseValues() {
		for (int i = 2; i < NameColumn.getChildren().size(); i++) { // setValues from Cwe
			String valueName = ((Label) NameColumn.getChildren().get(i)).getText();
			HBox hh = (HBox) (ValueColumn.getChildren().get(i));
			TextField h = (TextField) (hh.getChildren().get(1));
			h.setText(hero.getRawValueByName(valueName).getNumber() + "");
			System.out.println(valueName + "  " + hero.getRawValueByName(valueName).getNumber());
		}

	}

	private void updateSumValue() {

		while (SumColumn.getChildren().size() > 2) { // clearLists
			SumColumn.getChildren().remove(2);
		}

		for (int i = 2; i < NameColumn.getChildren().size(); i++) {
			Value valuebyName = hero.getValuebyName(Hero.baseValueList.get(i - 2).getName());
			Label label = new Label("" + valuebyName.getNumber());
			label.setFont(new Font(16));
			label.setTextAlignment(TextAlignment.CENTER);
			SumColumn.setAlignment(Pos.CENTER);
			SumColumn.getChildren().add(label);
		}

	}

	private void updateGPCount() {
		int sum = 0;
		for (int i = 2; i < ValueColumn.getChildren().size(); i++) {
			HBox hh = (HBox) (ValueColumn.getChildren().get(i));
			TextField h = (TextField) (hh.getChildren().get(1));

			sum += Integer.parseInt(h.getText());
		}
		if (layerChoiceBox.getSelectionModel().getSelectedItem() != null) {
			sum += (layerChoiceBox.getSelectionModel().getSelectedItem() - 1) * 10;
		}

		if (hero.getLvL() == 0) {
			gp = Hero.START_GP - sum;
			gp = gp - giftCost - domainCost + handicapCost;
		} else {
			int lvlsum = 0;
			for (Value v : levelUp) {
				if (!v.getName().equals("LP") && !v.getName().equals("MP"))
					lvlsum += v.getNumber();
			}
			gp = 5 - lvlsum;
			gp = gp - giftCost - domainCost + handicapCost;
		}

		// Gift,Handicap,Domain

		// setText
		gPCount.setText("" + gp);
		if (gp < 0) {
			gPCount.setTextFill(Color.RED);
			hero.setSomeWrongValues(true);
			lvlUpButton.setDisable(true);
		} else {
			gPCount.setTextFill(Color.BLACK);
			if (!hero.isSomeWrongValues()) {
				hero.setSomeWrongValues(false);
				lvlUpButton.setDisable(false);
			}
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
		Culture cs = CultureList.getSelectionModel().getSelectedItem();
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

			hero.setCwE(cWe);

			boolean someWrongValues = false;

			while (MinColumn.getChildren().size() > 2) { // clearLists
				MinColumn.getChildren().remove(2);
				MaxColumn.getChildren().remove(2);
				BonusColumn.getChildren().remove(2);
			}

			for (int i = 2; i < NameColumn.getChildren().size(); i++) { // setValues
																		// from
																		// cWe
				String valueName = ((Label) NameColumn.getChildren().get(i)).getText();

				HBox hh = (HBox) (ValueColumn.getChildren().get(i));
				TextField h = (TextField) (hh.getChildren().get(1));

				int value = 0;
				try {
					value = Integer.parseInt(h.getText());

				} catch (NumberFormatException g) {

				}

				int min = hero.getMinValueByName(valueName).getNumber();
				Label minLabel = new Label(min + "");
				minLabel.setFont(new Font(16));
				minLabel.setTextAlignment(TextAlignment.RIGHT);

				MinColumn.getChildren().add(minLabel);

				if (value < min) {
					minLabel.setTextFill(Color.RED);
					someWrongValues = true;
				}

				int max = hero.getMaxValueByName(valueName).getNumber();
				Label maxLabel = new Label(max + "");
				maxLabel.setFont(new Font(16));
				maxLabel.setTextAlignment(TextAlignment.RIGHT);
				MaxColumn.setAlignment(Pos.CENTER);
				MaxColumn.getChildren().add(maxLabel);

				if (value > max) {
					maxLabel.setTextFill(Color.RED);
					someWrongValues = true;
				}

				Label bonusLabel = new Label(cWe.getBonusValueByName(valueName).getNumber() + "");
				bonusLabel.setFont(new Font(16));
				bonusLabel.setTextAlignment(TextAlignment.CENTER);
				BonusColumn.setAlignment(Pos.CENTER);
				BonusColumn.getChildren().add(bonusLabel);

			}
			hero.setSomeWrongValues(someWrongValues);
			lvlUpButton.setDisable(someWrongValues);
		}

	}

	@FXML
	void selectDomain(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Domain d = allDomains.getSelectionModel().getSelectedItem();
			selectedDomain.getItems().add(d);
			allDomains.getItems().remove(d);
			hero.getDomains().add(d);
		}
		domainCost = 0;
		for (Domain d : selectedDomain.getItems()) {
			domainCost += d.getCost();
		}
		domainCostLabel.setText("" + domainCost);
		updateGPCount();
	}

	@FXML
	void selectGift(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Gift g = allGifts.getSelectionModel().getSelectedItem();
			selectedGifts.getItems().add(g);
			allGifts.getItems().remove(g);
			hero.getGifts().add(g);
		}
		giftCost = 0;
		for (Gift g : selectedGifts.getItems()) {
			giftCost += g.getCost();
		}
		giftCostLabel.setText("" + giftCost);
		updateGPCount();
	}

	@FXML
	void selectHandicap(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Handicap h = allHandicaps.getSelectionModel().getSelectedItem();
			selectedHandicap.getItems().add(h);
			allHandicaps.getItems().remove(h);
			hero.getHandicaps().add(h);
		}
		handicapCost = 0;
		for (Handicap h : selectedHandicap.getItems()) {
			handicapCost += h.getCost();
		}
		handicapCostLabel.setText("" + handicapCost);
		updateGPCount();
	}

	@FXML
	void deselectDomain(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Domain d = selectedDomain.getSelectionModel().getSelectedItem();
			allDomains.getItems().add(d);
			selectedDomain.getItems().remove(d);
			hero.getDomains().remove(d);
		}
		domainCost = 0;
		for (Domain d : selectedDomain.getItems()) {
			domainCost += d.getCost();
		}
		domainCostLabel.setText("" + domainCost);
		updateGPCount();
	}

	@FXML
	void deselectGift(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Gift g = selectedGifts.getSelectionModel().getSelectedItem();
			allGifts.getItems().add(g);
			selectedGifts.getItems().remove(g);
			hero.getGifts().remove(g);
		}
		giftCost = 0;
		for (Gift g : selectedGifts.getItems()) {
			giftCost += g.getCost();
		}
		giftCostLabel.setText("" + giftCost);
		updateGPCount();
	}

	@FXML
	void deselectHandicap(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Handicap h = selectedHandicap.getSelectionModel().getSelectedItem();
			allHandicaps.getItems().add(h);
			selectedHandicap.getItems().remove(h);
			hero.getHandicaps().remove(h);
		}
		handicapCost = 0;
		for (Handicap h : selectedHandicap.getItems()) {
			handicapCost += h.getCost();
		}
		handicapCostLabel.setText("" + handicapCost);
		updateGPCount();
	}

	@FXML
	void startTest(ActionEvent event) {
		testOutput.setText("teset1");
		for (Value v : hero.getValues()) {
			testOutput.setText(testOutput.getText() + "\n" + v.getName() + " : " + v.getNumber() + "=> "
					+ Calculator.calc(hero, v.getTerm()));
		}

		updateFightValues();

	}

	private void updateFightValues() {
		// clearAll
		LPBox.getChildren().clear();
		MPBox.getChildren().clear();

		while (MagieBox.getChildren().size() > 2) { // clearLists
			MagieBox.getChildren().remove(2);
		}
		while (NahkampfBox.getChildren().size() > 2) { // clearLists
			NahkampfBox.getChildren().remove(2);
		}
		while (AllgemeineBox.getChildren().size() > 2) { // clearLists
			AllgemeineBox.getChildren().remove(2);
		}

		// LPMP

		String lpString = hero.getValuebyName("LP").getName() + ": " + hero.getValuebyName("LP").getNumber();
		Label LPlabel = new Label(lpString);
		LPlabel.setFont(new Font(14));
		LPlabel.setTextAlignment(TextAlignment.CENTER);
		LPlabel.setTooltip(new Tooltip("Element-Bonus: " + hero.getCwE().getBonusValueByName("LP").getNumber()
				+ "\n LevelUp-Bonus: " + hero.getLvlValuesByName("LP").getNumber()));

		String mpString = hero.getValuebyName("MP").getName() + ": " + hero.getValuebyName("MP").getNumber();
		Label MPlabel = new Label(mpString);
		MPlabel.setFont(new Font(14));
		MPlabel.setTextAlignment(TextAlignment.CENTER);
		MPlabel.setTooltip(new Tooltip("Element-Bonus: " + hero.getCwE().getBonusValueByName("MP").getNumber()
				+ "\n LevelUp-Bonus: " + hero.getLvlValuesByName("MP").getNumber()));

		LPBox.getChildren().add(LPlabel);
		MPBox.getChildren().add(MPlabel);

		// create Lists
		ArrayList<String> MagieListe = new ArrayList<String>();
		MagieListe.add("MAT");
		MagieListe.add("MPA");
		MagieListe.add("MPAW");
		MagieListe.add("MTP");
		MagieListe.add("ZWZ");

		ArrayList<String> NahkampfListe = new ArrayList<String>();
		NahkampfListe.add("AT");
		NahkampfListe.add("PA");
		NahkampfListe.add("PAW");
		NahkampfListe.add("TP");
		NahkampfListe.add("AU");

		ArrayList<String> AllgemeinListe = new ArrayList<String>();
		AllgemeinListe.add("AW");
		AllgemeinListe.add("ZI");
		AllgemeinListe.add("INI");

		for (String s : MagieListe) {
			String String = hero.getValuebyName(s).getName() + ": " + hero.getValuebyName(s).getNumber();
			Label label = new Label(String);
			label.setFont(new Font(14));
			label.setTextAlignment(TextAlignment.CENTER);
			MagieBox.getChildren().add(label);
		}

		for (String s : NahkampfListe) {
			String String = hero.getValuebyName(s).getName() + ": " + hero.getValuebyName(s).getNumber();
			Label label = new Label(String);
			label.setFont(new Font(14));
			label.setTextAlignment(TextAlignment.CENTER);
			NahkampfBox.getChildren().add(label);
		}

		for (String s : AllgemeinListe) {
			String String = hero.getValuebyName(s).getName() + ": " + hero.getValuebyName(s).getNumber();
			Label label = new Label(String);
			label.setFont(new Font(14));
			label.setTextAlignment(TextAlignment.CENTER);
			AllgemeineBox.getChildren().add(label);
		}
	}

	@FXML
	void lvlUpClicked(ActionEvent event) {
		System.out.println(hero.isSomeWrongValues());
		if (hero.lvlUpPossible()) {
			levelUp = new ArrayList<Value>();
			hero.LvlUP(levelUp);
			this.ElementList.setDisable(true);
			this.CultureList.setDisable(true);

		}
		LvlCount.setText(hero.getLvL() + "");
		EpField.setText(hero.getEP() + "");

		changePossibilitiesAfterlvlUp();

		allUpdates();
	}

	private void changePossibilitiesAfterlvlUp() {
		if (hero.getLvL() > 0) { // Not a new Hero
			for (Node n : ValueColumn.getChildren()) {
				if (n.getClass().equals(HBox.class)) {
					((HBox) n).getChildren().get(1).setDisable(true);
				}
			}
			CultureList.setDisable(true);
			ElementList.setDisable(true);
			layerChoiceBox.setDisable(true);
			playerName.setDisable(true);
			heroName.setDisable(true);
			cultureRadio.setVisible(false);
			elementRadio.setVisible(false);
		} else { // a new Hero
			CultureList.setDisable(false);
			ElementList.setDisable(false);
			layerChoiceBox.setDisable(false);
			playerName.setDisable(false);
			heroName.setDisable(false);
			cultureRadio.setVisible(true);
			elementRadio.setVisible(true);
		}
	}

	@FXML
	void goToTermScene(ActionEvent event) {
		// Main.primStage.setScene(SpellBookScene.spellBookScene);
	}

	@FXML
	void startValues(ActionEvent event) {

	}

	@FXML
	void backButtonClicked(ActionEvent event) {
		HeroOverviewScene.controller.updateHeroList();
		Main.primStage.setScene(HeroOverviewScene.heroOverviewScene);
		hero = null;
	}

	@FXML
	void addEPClicked(ActionEvent event) { // TODO: better give ep system
		int addEp = 0;
		try {
			addEp = Integer.parseInt(addEpField.getText());
			addEpField.setText("");
		} catch (NumberFormatException e) {
		}

		hero.addEP(addEp);
		EpField.setText(hero.getEP() + "");
	}

	@FXML
	void mainMenueClicked(ActionEvent event) {
		Main.primStage.setScene(Main.mainMenueScene);
		hero = null;
	}

	public void setHero(Hero hero) {

		loadStuff();

		this.hero = hero;
		this.heroName.setText(hero.getName());
		if(hero.getMyPlayer()==null) {
			this.playerName.getSelectionModel().select(Player.getPlayerbyName("dummyPlayer"));
		} else {
			this.playerName.getSelectionModel().select(hero.getMyPlayer());
		}
		EpField.setText(hero.getEP() + "");
		layerChoiceBox.getSelectionModel().select(hero.getLayer());
		LvlCount.setText(hero.getLvL() + "");

		changePossibilitiesAfterlvlUp();

		ElementList.getSelectionModel().clearSelection();
		CultureList.getSelectionModel().clearSelection();
		layerChoiceBox.getSelectionModel().select(hero.getLayer());
		if (hero.getCwE() != null) {
			CultureList.getSelectionModel().select(hero.getCwE().getCulture());
			ElementList.getSelectionModel().select(hero.getCwE().getElement());
		}

		for (int i = 2; i < NameColumn.getChildren().size(); i++) { // setValues from Hero
			String valueName = ((Label) NameColumn.getChildren().get(i)).getText();
			HBox hh = (HBox) (ValueColumn.getChildren().get(i));
			TextField h = (TextField) (hh.getChildren().get(1));
			h.setText(hero.getRawValueByName(valueName).getNumber() + "");
			System.out.println(valueName + "  " + hero.getRawValueByName(valueName).getNumber());
		}

		setGiftHandicapDomain();
		updateGPCount();
	}

	private void setGiftHandicapDomain() {

		this.selectedGifts.getItems().clear();
		selectedGifts.getItems().addAll(hero.getGifts());
		allGifts.getItems().removeAll(hero.getGifts());

		this.selectedHandicap.getItems().clear();
		selectedHandicap.getItems().addAll(hero.getHandicaps());
		allHandicaps.getItems().removeAll(hero.getHandicaps());

		this.selectedDomain.getItems().clear();
		selectedDomain.getItems().addAll(hero.getDomains());
		allDomains.getItems().removeAll(hero.getDomains());
	}

}
