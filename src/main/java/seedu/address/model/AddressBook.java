package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.grouping.Group;
import seedu.address.model.grouping.House;
import seedu.address.model.grouping.UniqueGroupList;
import seedu.address.model.grouping.UniqueHouseList;
import seedu.address.model.participant.Participant;
import seedu.address.model.participant.UniqueParticipantList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameParticipant comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final ObservableMap<String, Integer> ageData = FXCollections.observableHashMap();
    private final ObservableMap<String, Integer> majorData = FXCollections.observableHashMap();
    private final ObservableMap<String, Integer> sexData = FXCollections.observableHashMap();

    private final UniqueParticipantList participants;
    private final UniqueGroupList groups;
    private final UniqueHouseList houses;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
    * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
    * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    *
    * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
    *   among constructors.
    */ {
        participants = new UniqueParticipantList();
        groups = new UniqueGroupList();
        houses = new UniqueHouseList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    /**
     * Replaces the contents of the participant list with {@code participants}.
     * {@code participants} must not contain duplicate participants.
     */
    public void setParticipants(List<Participant> participants) {
        this.participants.setParticipants(participants);
        indicateModified();
    }

    /**
     * Add data of a new participant
     * @param toAdd
     */
    public void addData (Participant toAdd) {
        ageData.put(toAdd.getBirthday().getAge(), (!ageData.containsKey(toAdd.getBirthday().getAge())) ? 1
                : ageData.get(toAdd.getBirthday().getAge()) + 1);
        majorData.put(toAdd.getMajor().value, (!majorData.containsKey(toAdd.getMajor().value)) ? 1
                : majorData.get(toAdd.getMajor().value) + 1);
        sexData.put(toAdd.getSex().value, (!sexData.containsKey(toAdd.getSex().value)) ? 1
                : sexData.get(toAdd.getSex().value) + 1);
    }

    /**
     * Delete data of a participant
     */
    public void deleteData (Participant toDelete) {
        ageData.put(toDelete.getBirthday().getAge(), ageData.get(toDelete.getBirthday().getAge()) - 1);
        majorData.put(toDelete.getMajor().value, majorData.get(toDelete.getMajor().value) - 1);
        sexData.put(toDelete.getSex().value, sexData.get(toDelete.getSex().value) - 1);
    }

    /**
    * Resets the existing data of this {@code AddressBook} with {@code newData}.
    */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setParticipants(newData.getParticipantList());
        setGroups(newData.getGroupList());
        setHouses(newData.getHouseList());

        ageData.putAll(newData.getAgeData());
        majorData.putAll(newData.getMajorData());
        sexData.putAll(newData.getSexData());
    }

    /**
     * Replaces the contents of the participant list with {@code participants}.
     * {@code participants} must not contain duplicate participants.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
        indicateModified();
    }

    /**
     * Replaces the contents of the participant list with {@code participants}.
     * {@code participants} must not contain duplicate participants.
     */
    public void setHouses(List<House> houses) {
        this.houses.setHouses(houses);
        indicateModified();
    }

    //// participant-level operations

    /**
     * Returns true if a participant with the same identity as {@code participant} exists in the address book.
     */
    public boolean hasParticipant(Participant participant) {
        requireNonNull(participant);
        return participants.contains(participant);
    }

    /**
     * Adds a participant to the address book.
     * The participant must not already exist in the address book.
     */
    public void addParticipant(Participant p) {
        participants.add(p);
        indicateModified();
    }

    /**
     * Replaces the given participant {@code target} in the list with {@code editedParticipant}.
     * {@code target} must exist in the address book.
     * The participant identity of {@code editedParticipant}
     * must not be the same as another existing participant in the address book.
     */
    public void setParticipant(Participant target, Participant editedParticipant) {
        requireNonNull(editedParticipant);

        participants.setParticipant(target, editedParticipant);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeParticipant(Participant key) {
        participants.remove(key);
        indicateModified();
    }

    //// group-level operations

    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.contains(group);
    }

    /**
     * Adds a group to the address book.
     * The group must not already exist in the address book.
     */
    public void addGroup(Group g) {
        groups.add(g);
        indicateModified();
    }

    /**
     * Replaces the given group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the address book.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the address book.
     */
    public void setGroup(Group target, Group editedGroup) {
        requireNonNull(editedGroup);

        groups.setGroup(target, editedGroup);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeGroup(Group key) {
        groups.remove(key);
        indicateModified();
    }

    /**
    * Returns a group with the correct house name
     */
    public Group getGroup(Group group) {
        return this.groups.getGroup(group);
    }

    //// house-level operations
    /**
     * Returns true if a house with the same identity as {@code house} exists in the address book.
     */
    public boolean hasHouse(House house) {
        requireNonNull(house);
        return houses.contains(house);
    }

    /**
     * Adds a house to the address book.
     * The house must not already exist in the address book.
     */
    public void addHouse(House house) {
        houses.add(house);
        indicateModified();
    }

    /**
     * Replaces the given house {@code target} in the list with {@code editedHouse}.
     * {@code target} must exist in the address book.
     * The house identity of {@code editedHouse} must not be the same as another existing house in the address book.
     */
    public void setHouse(House target, House editedHouse) {
        requireNonNull(editedHouse);

        houses.setHouse(target, editedHouse);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeHouse(House key) {
        houses.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    public ObservableMap<String, Integer> getAgeData() {
        ageData.clear();
        ObservableMap<String, Integer> rawData = FXCollections.observableHashMap();
        for (Participant p : participants) {
            String key = p.getBirthday().getAge();
            Integer value = (rawData.containsKey(key)) ? (rawData.get(key) + 1) : (1);
            rawData.put(key, value);
        }

        for (String key : rawData.keySet()) {
            ageData.put(key + " (" + rawData.get(key) + ")", rawData.get(key));
        }
        return FXCollections.observableMap(ageData);
    }

    public ObservableMap<String, Integer> getMajorData() {
        majorData.clear();
        ObservableMap<String, Integer> rawData = FXCollections.observableHashMap();
        for (Participant p : participants) {
            String key = p.getMajor().value;
            Integer value = (rawData.containsKey(key)) ? (rawData.get(key) + 1) : (1);
            rawData.put(key, value);
        }

        for (String key : rawData.keySet()) {
            majorData.put(key + " (" + rawData.get(key) + ")", rawData.get(key));
        }
        return FXCollections.unmodifiableObservableMap(majorData);
    }

    public ObservableMap<String, Integer> getSexData() {
        sexData.clear();
        ObservableMap<String, Integer> rawData = FXCollections.observableHashMap();
        for (Participant p : participants) {
            String key = p.getSex().value;
            Integer value = (rawData.containsKey(key)) ? (rawData.get(key) + 1) : (1);
            rawData.put(key, value);
        }

        for (String key : rawData.keySet()) {
            sexData.put(key + " (" + rawData.get(key) + ")", rawData.get(key));
        }
        return FXCollections.unmodifiableObservableMap(sexData);
    }

    @Override
    public String toString() {
        return participants.asUnmodifiableObservableList().size() + " participants";
        // TODO: refine later
    }

    @Override
    public ObservableList<Participant> getParticipantList() {
        return participants.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<House> getHouseList() {
        return houses.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && participants.equals(((AddressBook) other).participants));
    }

    @Override
    public int hashCode() {
        return participants.hashCode();
    }
}
