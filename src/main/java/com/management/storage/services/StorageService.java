package com.management.storage.services;

import com.management.storage.dto.response.*;
import com.management.storage.model.Item;
import com.management.storage.model.ItemStorage;
import com.management.storage.model.Storage;
import com.management.storage.model.composite.ItemStorageId;
import com.management.storage.repository.ItemRepository;
import com.management.storage.repository.ItemStorageRepository;
import com.management.storage.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StorageService {

    StorageRepository storageRepository;

    ItemStorageRepository itemStorageRepository;

    ItemRepository itemRepository;

    public List<StorageItemResponseDto> findAll() {
        return mapStorageDetailsToDto(storageRepository.findAll(), storageRepository.itemsInStorageFullDetails());
    }

    public Storage findById(final Long id) {
        return storageRepository.getById(id);
    }

    public List<StorageItemsDetailResponseDto> findAllItems() {
        return mapStorageItemDetailsToDto(storageRepository.allItemsInStorage(), storageRepository.allItemsInStorageNotMounted());
    }

    @Transactional
    public Storage createOrUpdate(final Storage storage) {
        return storageRepository.save(storage);
    }

    public void deleteById(final Long id) {
        storageRepository.deleteById(id);
    }

    public List<ItemsCountResponseDto> countItems() {
        return storageRepository.sumAllItemsInStorage().stream().map(
                itemCount -> new ItemsCountResponseDto(translateItemType(itemCount.getValue()),itemCount.getQuantity()))
                .collect(Collectors.toList());
    }

    private List<StorageItemResponseDto> mapStorageDetailsToDto(List<Storage> storages, List<FullDetailItemsInStorageDto> detailItemsInStorages) {
        return storages.stream().map(
                storage ->
                        StorageItemResponseDto.builder()
                        .id(storage.getId())
                        .name(storage.getName())
                        .location(storage.getLocation())
                        .created(storage.getCreated())
                        .modified(storage.getModified())
                        .itemStorages(
                                detailItemsInStorages.stream().map(
                                        detailItem -> Objects.equals(storage.getId(), detailItem.getStorageid()) ?
                                                mapItemToStorage(storage, detailItem) : null
                                ).collect(Collectors.toSet())
                        )
                        .build()
        ).collect(Collectors.toList());
        /*
        List<StorageItemResponseDto> fullDetails = new ArrayList<>();

        for (Storage storage : storages) {
            StorageItemResponseDto storageResponse = new StorageItemResponseDto();
            storageResponse.setId(storage.getId());
            storageResponse.setName(storage.getName());
            storageResponse.setLocation(storage.getLocation());
            storageResponse.setCreated(storage.getCreated());
            storageResponse.setModified(storage.getModified());

            Set<ItemStorageResponseDto> itemStorageResponses = new HashSet<>();
            for (FullDetailItemsInStorageDto detailItem : detailItemsInStorages) {
                if (Objects.equals(storage.getId(), detailItem.getStorageid())) {
                    ItemStorageResponseDto itemStorageResponse = new ItemStorageResponseDto();
                    ItemStorageId itemStorageId = new ItemStorageId(detailItem.getItemid(), storage.getId());
                    ItemStorage currentItemStorage = itemStorageRepository.getById(itemStorageId);
                    itemStorageResponse.setId(currentItemStorage.getId());

                    Item item = itemRepository.getById(detailItem.getItemid());
                    itemStorageResponse.setItem(item);
                    itemStorageResponse.setValue(item.getValue());
                    itemStorageResponse.setDescription(item.getDescription());
                    if (item.getColor() != null) {
                        itemStorageResponse.setColor(item.getColor().getValue());
                    } else {
                        itemStorageResponse.setColor(null);
                    }
                    itemStorageResponse.setGuideneeded(item.getGuide_needed());
                    itemStorageResponse.setStorage(storage);
                    if (detailItem.getQuantity() != null) {
                        itemStorageResponse.setQuantity(detailItem.getQuantity());
                    } else {
                        itemStorageResponse.setQuantity(0);
                    }
                    if (detailItem.getMountedquantity() != null) {
                        itemStorageResponse.setNotMountedQuantity(detailItem.getMountedquantity());
                    } else {
                        itemStorageResponse.setNotMountedQuantity(0);
                    }

                    Integer avaiableQuantity = itemStorageResponse.getQuantity() - itemStorageResponse.getNotMountedQuantity();
                    itemStorageResponse.setAvaliableQuantity(avaiableQuantity);

                    itemStorageResponses.add(itemStorageResponse);
                }

            }
            storageResponse.setItemStorages(itemStorageResponses);
            fullDetails.add(storageResponse);
        }
        return fullDetails; */
    }

    private List<StorageItemsDetailResponseDto> mapStorageItemDetailsToDto(List<ItemsInStorageResponse> allItemsInStorage, List<ItemsInStorageResponse> allItemsInStorageNotMounted) {

        return allItemsInStorage.stream().map(itemAll -> {

            AtomicReference<Integer> notMountedQuantity = new AtomicReference<>(0);
            AtomicReference<Integer> avaliableQuantity = new AtomicReference<>(itemAll.getQuantity());

            allItemsInStorageNotMounted.forEach(itemNotMounted -> {

                if (Objects.equals(itemAll.getId(), itemNotMounted.getId())) {
                    notMountedQuantity.set(itemNotMounted.getQuantity());
                    avaliableQuantity.set(avaliableQuantity.get() - notMountedQuantity.get());
                }
            });
            return new StorageItemsDetailResponseDto(itemAll.getId(), itemAll.getValue(), itemAll.getDescription(), itemAll.getType(), itemAll.getColor(), itemAll.getGuideneeded(), itemAll.getQuantity(), notMountedQuantity.get(), avaliableQuantity.get());
        }).collect(Collectors.toList());

        /*
        List<StorageItemsDetailResponseDto> storageItemsDetailResponses = new ArrayList<>();
        for (ItemsInStorageResponse itemAll : allItemsInStorage) {
            Integer notMountedQuantity = 0;
            Integer avaliableQuantity = itemAll.getQuantity();

            for (ItemsInStorageResponse itemNotMounted : allItemsInStorageNotMounted) {

                if (Objects.equals(itemAll.getId(), itemNotMounted.getId())) {
                    notMountedQuantity = itemNotMounted.getQuantity();
                    avaliableQuantity = avaliableQuantity - notMountedQuantity;
                }

            }
            storageItemsDetailResponses.add(
                    new StorageItemsDetailResponseDto(itemAll.getId(), itemAll.getValue(), itemAll.getDescription(), itemAll.getType(), itemAll.getColor(), itemAll.getGuideneeded(), itemAll.getQuantity(), notMountedQuantity, avaliableQuantity)
            );

        }
        return storageItemsDetailResponses;
        */

    }

    private String translateItemType(String value) {
        String realName = "";
        switch (value) {
            case "DOOR":
                realName = "Vrata";
                break;
            case "MOTOR":
                realName = "Motori";
                break;
            case "GUIDE":
                realName = "Vodilice";
                break;
            case "SUSPENSION":
                realName = "Ovjesi";
                break;
        }
        return realName;
    }

    public ItemStorageResponseDto mapItemToStorage(Storage storage, FullDetailItemsInStorageDto detailItem) {
        ItemStorageResponseDto itemStorageResponse = new ItemStorageResponseDto();

        ItemStorageId itemStorageId = ItemStorageId.builder()
                .itemId(detailItem.getItemid())
                .storageId(detailItem.getStorageid())
                .build();

        ItemStorage currentItemStorage = itemStorageRepository.getById(itemStorageId);
        itemStorageResponse.setId(currentItemStorage.getId());

        Item item = itemRepository.getById(detailItem.getItemid());
        itemStorageResponse.setItem(item);
        itemStorageResponse.setValue(item.getValue());
        itemStorageResponse.setDescription(item.getDescription());

        if (Objects.nonNull(item.getColor())) {
            itemStorageResponse.setColor(item.getColor().getValue());
        } else {
            itemStorageResponse.setColor(null);
        }


        itemStorageResponse.setGuideneeded(item.getGuide_needed());
        itemStorageResponse.setStorage(storage);

        if (Objects.nonNull(detailItem.getQuantity())) {
            itemStorageResponse.setQuantity(detailItem.getQuantity());
        } else {
            itemStorageResponse.setQuantity(0);
        }

        if (Objects.nonNull(detailItem.getMountedquantity())) {
            itemStorageResponse.setNotMountedQuantity(detailItem.getMountedquantity());
        } else {
            itemStorageResponse.setNotMountedQuantity(0);
        }

        Integer availableQuantity = itemStorageResponse.getQuantity() - itemStorageResponse.getNotMountedQuantity();
        itemStorageResponse.setAvaliableQuantity(availableQuantity);

        return itemStorageResponse;
    }
}
